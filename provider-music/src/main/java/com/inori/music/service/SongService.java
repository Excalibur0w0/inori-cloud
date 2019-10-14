package com.inori.music.service;

import com.inori.music.pojo.TblSong;

public interface SongService {
    TblSong getById(String songId);

    void likeSong(String songId, String userId);

    TblSong searchSong(String keywords);

    void dislikeSong(String songId, String userId);
}
