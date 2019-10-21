package com.inori.music.service.imp;

import com.inori.music.dao.TblLikeSongDao;
import com.inori.music.dao.TblSheetSongDao;
import com.inori.music.dao.hbase.HSongDao;
import com.inori.music.pojo.TblLikeSong;
import com.inori.music.pojo.TblSheetSong;
import com.inori.music.pojo.TblSong;
import com.inori.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class HSongServiceImp implements SongService {
    @Autowired
    private HSongDao hSongDao;
    @Autowired
    private TblLikeSongDao tblLikeSongDao;
    @Autowired
    private TblSheetSongDao tblSheetSongDao;

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
    public TblSong searchSong(String keywords) {
        return null;
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
        TblSong example = new TblSong();
        example.setSongUploader(uploadUserId);

//        return tblSongDao.count(Example.of(example));
        return null;
    }

    @Override
    public List<TblSong> getAll() {
        return null;
    }
}
