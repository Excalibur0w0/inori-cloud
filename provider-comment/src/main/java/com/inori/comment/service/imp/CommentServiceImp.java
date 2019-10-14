package com.inori.comment.service.imp;

import com.inori.comment.dao.TblCommentDao;
import com.inori.comment.pojo.TblComment;
import com.inori.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public class CommentServiceImp implements CommentService {
    @Autowired
    private TblCommentDao tblCommentDao;

    @Override
    public TblComment getById(String commentId) {
        return tblCommentDao.findById(commentId).orElse(null);
    }

    @Override
    public void deleteById(String commentId) {
        tblCommentDao.deleteById(commentId);
    }

    @Override
    public void banById(String commentId) {
        TblComment cmt = tblCommentDao.findById(commentId).orElse(null);

        if (cmt != null) {
            cmt.setCommentStatus("banned");
            tblCommentDao.save(cmt);
        }
    }

    @Transactional
    @Override
    public void banAllByUserId(String userId) {
        List<TblComment> cmts = tblCommentDao.findTblCommentsByUserId(userId);

        if (cmts != null && cmts.size() >= 1) {
            try {
                cmts.forEach(mem -> mem.setCommentStatus("banned"));
                tblCommentDao.saveAll(cmts);
            } catch (Exception e) {
                throw new RuntimeException("禁用用户:userId: " + userId + "的所有评论时出错!");
            }
        } else {
            throw new RuntimeException("该用户不存在或没有发表评论!");
        }
    }

    @Transactional
    @Override
    public void banAllBySongId(String songId) {
        List<TblComment> cmts = tblCommentDao.findTblCommentsBySongId(songId);

        if (cmts != null && cmts.size() >= 1) {
            try {
                cmts.forEach(mem -> mem.setCommentStatus("banned"));
                tblCommentDao.saveAll(cmts);
            } catch (Exception e) {
                throw new RuntimeException("禁用歌曲:songId: " + songId + "的所有评论时出错!");
            }
        } else {
            throw new RuntimeException("该歌曲不存在或还没有人发表评论!");
        }
    }

    @Override
    public List<TblComment> getAllBySongId(String songId) {
        TblComment cmt = new TblComment();
        cmt.setSongId(songId);
        Example<TblComment> example = Example.of(cmt);

        return tblCommentDao.findAll(example);
    }

    @Override
    public List<TblComment> getAllByUserId(String userId) {
        TblComment cmt = new TblComment();
        cmt.setUserId(userId);

        return tblCommentDao.findAll(Example.of(cmt));
    }

    @Override
    public void likeComment(String commentId, String userId) {
        TblComment cmt = tblCommentDao.findById(commentId).orElse(null);

        if (cmt != null) {
            cmt.setLike(cmt.getLike() + 1);
        } else {
            throw new RuntimeException("没有该评论! commentId: " + commentId);
        }
    }

    @Override
    public void dislikeComment(String commentId, String userId) {
        TblComment cmt = tblCommentDao.findById(commentId).orElse(null);

        if (cmt != null) {
            cmt.setDislike(cmt.getDislike() + 1);
        } else {
            throw new RuntimeException("没有该评论! commentId: " + commentId);
        }
    }

    @Override
    public TblComment makeComment(String content, String songId, String userId) {
        TblComment comment = new TblComment();
        Date now = new Date(System.currentTimeMillis());

        comment.setUserId(userId);
        comment.setSongId(songId);
        comment.setCommentStatus("active");
        comment.setContent(content);

        return tblCommentDao.save(comment);
    }

    @Override
    public TblComment editComment(String content, String commentId) {
        TblComment cmt = tblCommentDao.findById(commentId).orElse(null);

        if (cmt != null) {
            cmt.setContent(content);
            return tblCommentDao.save(cmt);
        } else {
            throw new RuntimeException("没有找到该评论!");
        }
    }
}
