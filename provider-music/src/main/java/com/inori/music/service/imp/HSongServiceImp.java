package com.inori.music.service.imp;

import com.inori.music.dao.TblLikeSongDao;
import com.inori.music.dao.TblSheetDao;
import com.inori.music.dao.TblSheetSongDao;
import com.inori.music.dao.hbase.HSongChunkDao;
import com.inori.music.dao.hbase.HSongDao;
import com.inori.music.dto.SongDTO;
import com.inori.music.pojo.*;
import com.inori.music.service.SongService;
import com.inori.music.utils.MusicHelper;
import com.netflix.discovery.converters.Auto;
import jdk.internal.util.xml.impl.Input;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Log4j2
@Service
public class HSongServiceImp implements SongService {
    @Autowired
    private HSongDao hSongDao;
    @Autowired
    private HSongChunkDao hSongChunkDao;
    @Autowired
    private TblLikeSongDao tblLikeSongDao;
    @Autowired
    private TblSheetSongDao tblSheetSongDao;
    @Autowired
    private TblSheetDao tblSheetDao;

    @Override
    public TblSong getById(String songId) {
        return hSongDao.getById(songId);
    }

    @Override
    public void likeSong(String songId, String userId) {
        TblSong song = hSongDao.getById(songId);

        if (song != null) {
            TblLikeSong var = new TblLikeSong();
            var.setSongId(song.getUuid());
            var.setUserId(userId);
            var.setUuid(UUID.randomUUID().toString());

            if (tblLikeSongDao.save(var) == null) {
                throw new RuntimeException("写入TblLikeSong错误!");
            }
        }
    }

    @Override
    public List<TblSong> searchAllSongs(String keywords) {
        return hSongDao.findAllByKeyWords(keywords);
    }

    @Override
    public boolean isDislikeSong(String songId, String userId) {
        TblLikeSong tblLikeSong = new TblLikeSong();
        tblLikeSong.setSongId(songId);
        tblLikeSong.setUserId(userId);
        TblLikeSong result = tblLikeSongDao.findOne(Example.of(tblLikeSong)).orElse(null);

        return result == null;
    }

    @Override
    public void dislikeSong(String songId, String userId) {
        TblLikeSong tblLikeSong = new TblLikeSong();
        tblLikeSong.setUserId(userId);
        tblLikeSong.setSongId(songId);

        TblLikeSong result = tblLikeSongDao.findOne(Example.of(tblLikeSong)).orElse(null);

        if (result != null) {
            tblLikeSongDao.delete(result);
        } else {
            throw new RuntimeException("试图删除不存在的关系： userId: " + userId + " songId" + songId);
        }
    }

    @Override
    public void collectSong(String sheetId, String songId, String userId) {
        TblSheet sheet = tblSheetDao.findById(sheetId).orElse(null);
        if (sheet == null) {
            throw new RuntimeException("歌单不存在");
        }
        if (!sheet.getShtCreator().equals(userId)) {
            throw new RuntimeException("不是该歌单的创建者");
        }

        TblSheetSong ss = new TblSheetSong();
        ss.setShtId(sheetId);
        ss.setSongId(songId);
        if (tblSheetSongDao.findOne(Example.of(ss)).orElse(null) != null) {
            throw new RuntimeException("已经收藏过该歌单");
        };
        ss.setUuid(UUID.randomUUID().toString());

        tblSheetSongDao.save(ss);
    }

    @Override
    public void cancelCollect(String sheetId, String songId, String userId) {
        TblSheet sheet = tblSheetDao.findById(sheetId).orElse(null);
        if (sheet == null) {
            throw new RuntimeException("歌单不存在");
        }
        if (!sheet.getShtCreator().equals(userId)) {
            throw new RuntimeException("不是该歌单的创建者");
        }

        TblSheetSong ss = new TblSheetSong();
        ss.setSongId(songId);
        ss.setShtId(sheetId);
        if (tblSheetSongDao.findOne(Example.of(ss)).orElse(null)  == null) {
            throw new RuntimeException("没有收藏该歌单");
        }
        tblSheetSongDao.delete(ss);
    }

    @Override
    public List<TblSong> getSongsByShtId(String shtId) {
        TblSheetSong example = new TblSheetSong();
        example.setShtId(shtId);
        List<TblSheetSong> sheetSongSet = tblSheetSongDao.findAll(Example.of(example));
        List<TblSong> resultSet = new LinkedList<>();

        // 性能可能很低，之后改
        sheetSongSet.forEach(ss -> {
            TblSong var = hSongDao.getById(ss.getSongId());
            if (var != null) {
                resultSet.add(var);
            }
        });

        return resultSet;
    }

    @Override
    public List<SongDTO> getSongsByUserLike(String userId) {
        TblLikeSong example = new TblLikeSong();
        example.setUserId(userId);
        List<SongDTO> result = new ArrayList<>();

        tblLikeSongDao.findAll(Example.of(example)).forEach(ls -> {
            TblSong s = hSongDao.getById(ls.getSongId());
            if (s != null) {
                SongDTO dto = new SongDTO();
                dto.setSong(s);
                dto.setIsFavorite(true);
                result.add(dto);
            }
        });

        return result;
    }

    @Override
    public List<TblSong> getUserFavoriteSongs(String userId) {
        List<TblSong> results = new ArrayList<>();
        TblLikeSong tblLikeSong = new TblLikeSong();
        tblLikeSong.setUserId(userId);

        List<TblLikeSong> likeSongs = tblLikeSongDao.findAll(Example.of(tblLikeSong));
        likeSongs.forEach(ls -> {
            results.add(hSongDao.getById(ls.getSongId()));
        });

        return results;
    }

    @Override
    public Long countSongsByShtId(String shtId) {
        TblSheetSong example = new TblSheetSong();
        example.setShtId(shtId);

        return tblSheetSongDao.count(Example.of(example));
    }

    @Override
    public List<TblSong> getSongsByUploader(String uploadUserId) {
        TblSong example = new TblSong();
        example.setSongUploader(uploadUserId);

        return hSongDao.findAllByUploader(uploadUserId);
    }

    @Override
    public Long countSongByUploader(String uploadUserId) {
        return new Long(hSongDao.findAllByUploader(uploadUserId).size());
    }

    @Override
    public List<TblSong> getAll() {
        return hSongDao.findAll();
    }

    @Override
    public List<TblSong> getSongsByAuthor(String author) {
        return hSongDao.findAllByAuthor(author);
    }

    @Transactional
    @Override
    public boolean uploadSingleSongChunk(String md5,
                                         MultipartFile file,
                                         Long curIndex,
                                         Long totalChunks) {

        try {
            if (curIndex < totalChunks) {
                InputStream in = file.getInputStream();
                // read
                byte[] buffer = new byte[1024];
                int len = 0;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                while ((len = in.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
                bos.close();

                hSongChunkDao.insert(md5, bos.toByteArray(), curIndex, totalChunks);
                return true;
            } else {
                throw new RuntimeException("文件块序数只能小于文件块总数!");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean afterCompletedUpload(String md5, String extension, String uploaderId) {
        try {
            // 将文件所有分片查询出来
            Date now = new Date(System.currentTimeMillis());
            TblSong song = new TblSong();
            song.setStorePath(md5);
            song.setFileType(extension);
            song.setUuid(md5);
            song.setSongName("default");
            song.setSongUploader(uploaderId);
            song.setSongAuthor("default");
            song.setSongAlbum("default");
            song.setCreatedAt(now);
            song.setUpdatedAt(now);

            InputStream mergeIn = hSongChunkDao.getAllChunksMergeInStream(md5);
            MusicHelper musicHelper = new MusicHelper(md5, extension);
            // 写入缓存磁盘
            musicHelper.write(mergeIn);
            // 设置MetaInfo
            musicHelper.setMP3MetaInfo(song);
//                    byte[] bytes = musicHelper.getMP3Image();

            hSongDao.insert(song);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Long> checkSongIntegrity(String md5, Long totalChunks) {
        return hSongChunkDao.checkAbsentChunk(md5, totalChunks);
    }

    @Override
    public Resource getSingleSongByMd5(String md5) {
        InputStream in = hSongChunkDao.getAllChunksMergeInStream(md5);
        Resource resource = new InputStreamResource(in);

        return resource;
    }

    @Override
    public List<SongDTO> wrapWithFavorite(List<TblSong> list, String userId) {
        List<SongDTO> results = new ArrayList<>();

        list.forEach(item -> {
            TblLikeSong tblLikeSong = new TblLikeSong();
            tblLikeSong.setUserId(userId);
            tblLikeSong.setSongId(item.getUuid());
            tblLikeSong = tblLikeSongDao.findOne(Example.of(tblLikeSong)).orElse(null);
            SongDTO songDTO = new SongDTO();
            songDTO.setSong(item);
            songDTO.setIsFavorite(tblLikeSong != null);

            results.add(songDTO);
        });

        return results;
    }
}
