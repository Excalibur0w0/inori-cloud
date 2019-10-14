package com.inori.music.service.imp;

import com.inori.music.dao.TblLikeSongDao;
import com.inori.music.dao.TblSongDao;
import com.inori.music.pojo.TblLikeSong;
import com.inori.music.pojo.TblSong;
import com.inori.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class SongServiceImp implements SongService {
    @Autowired
    private TblSongDao tblSongDao;
    @Autowired
    private TblLikeSongDao tblLikeSongDao;

    @Override
    public TblSong getById(String songId) {
        return tblSongDao.findById(songId).orElse(null);
    }

    @Override
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

}
