package com.inori.music.service.imp;

import com.inori.music.dao.TblLikeSongDao;
import com.inori.music.dao.TblSheetSongDao;
import com.inori.music.dao.TblSongDao;
import com.inori.music.pojo.TblLikeSong;
import com.inori.music.pojo.TblSheetSong;
import com.inori.music.pojo.TblSong;
import com.inori.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

//@Service
@Deprecated
public class SongServiceImp {
    private TblSongDao tblSongDao;
    private TblLikeSongDao tblLikeSongDao;
    private TblSheetSongDao tblSheetSongDao;

    public TblSong getById(String songId) {
        return tblSongDao.findById(songId).orElse(null);
    }

    public void likeSong(String songId, String userId) {
        tblSongDao.findById(songId).ifPresent(song -> {
            TblLikeSong var = new TblLikeSong();
            var.setSongId(song.getUuid());
            var.setUserId(userId);
            var.setUuid(UUID.randomUUID().toString());

            if (tblLikeSongDao.save(var) == null) {
                throw new RuntimeException("写入TblLikeSong错误!");
            }
        });
    }

    public List<TblSong> searchAllSongs(String keywords) {
        return null;
    }

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

    public List<TblSong> getSongsByShtId(String shtId) {
        TblSheetSong example = new TblSheetSong();
        example.setShtId(shtId);
        List<TblSheetSong> sheetSongSet = tblSheetSongDao.findAll(Example.of(example));
        List<TblSong> resultSet = new LinkedList<>();


        sheetSongSet.forEach(ss -> tblSongDao.findById(ss.getSongId())
                .ifPresent(var -> resultSet.add(var)));

        return resultSet;
    }

    public Long countSongsByShtId(String shtId) {
        TblSheetSong example = new TblSheetSong();
        example.setShtId(shtId);

        return tblSheetSongDao.count(Example.of(example));
    }

    public List<TblSong> getSongsByUploader(String uploadUserId) {
        TblSong example = new TblSong();
        example.setSongUploader(uploadUserId);

        return tblSongDao.findAll(Example.of(example));
    }

    public Long countSongByUploader(String uploadUserId) {
        TblSong example = new TblSong();
        example.setSongUploader(uploadUserId);

        return tblSongDao.count(Example.of(example));
    }

    public List<TblSong> getAll() {
        return tblSongDao.findAll();
    }

    public List<TblSong> getSongsByAuthor(String author) {
        return null;
    }

    public boolean uploadSingleSongChunk(String md5, String uploaderId, MultipartFile file, Long curIndex, Long totalChunks, String extension) {
        return false;
    }

    public List<Integer> checkSongIntegrity(String md5, Integer totalChunks) {
        return null;
    }

    public Resource getSingleSongByMd5(String md5) {
        return null;
    }

}
