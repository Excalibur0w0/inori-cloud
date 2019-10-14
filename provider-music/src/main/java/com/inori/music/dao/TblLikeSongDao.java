package com.inori.music.dao;

import com.inori.music.pojo.TblLikeSong;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TblLikeSongDao extends JpaRepository<TblLikeSong, String> {
}
