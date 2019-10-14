package com.inori.comment.dao;

import com.inori.comment.pojo.TblComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TblCommentDao extends JpaRepository<TblComment, String> {
    List<TblComment> findTblCommentsByUserId(String userId);

    List<TblComment> findTblCommentsBySongId(String songId);
}
