package com.inori.music.service;

import com.inori.music.pojo.TblSong;

import java.util.List;

public interface SongService {
    TblSong getById(String songId);

    void likeSong(String songId, String userId);

    TblSong searchSong(String keywords);

    void dislikeSong(String songId, String userId);

    List<TblSong> getSongsByShtId(String shtId);

    Long countSongsByShtId(String shtId);

    List<TblSong> getSongsByUploader(String uploadUserId);

    Long countSongByUploader(String uploadUserId);

    List<TblSong> getAll();
}
