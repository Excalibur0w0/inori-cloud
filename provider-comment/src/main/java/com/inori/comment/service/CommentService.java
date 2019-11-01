package com.inori.comment.service;

import com.inori.comment.pojo.TblComment;

import java.util.List;

public interface CommentService {
    TblComment getById(String commentId);
    void deleteById(String commentId);
    void banById(String commentId);
    void banAllByUserId(String userId);
    void banAllBySongId(String songId);
    List<TblComment> getAllBySongId(String songId);
    List<TblComment> getAllByUserId(String userId);
    List<TblComment> wrapWithUserInfo(List<TblComment> list, String auth);
    TblComment wrapWithUserInfo(TblComment comment, String auth);
    void likeComment(String commentId, String userId);
    void dislikeComment(String commentId, String userId);
    TblComment makeComment(String content, String songId, String userId);
    // 确保用户是自己
    TblComment editComment(String content, String commentId);
}
