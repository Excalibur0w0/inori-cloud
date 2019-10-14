package com.inori.music.dao;

import com.inori.music.pojo.TblSong;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TblSongDao extends JpaRepository<TblSong, String> {
}
