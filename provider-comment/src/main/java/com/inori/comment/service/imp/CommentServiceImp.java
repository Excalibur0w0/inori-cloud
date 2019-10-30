package com.inori.comment.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.inori.comment.dao.TblCommentDao;
import com.inori.comment.pojo.TblComment;
import com.inori.comment.service.CommentService;
import com.inori.comment.service.FetchUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class CommentServiceImp implements CommentService {
    @Autowired
    private TblCommentDao tblCommentDao;
    @Autowired
    private FetchUserService fetchUserService;

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

    /**
     *
     * @param cs
     * @param auth 目标接口需要权限认真
     * @return
     */
    @Override
    public List<TblComment> wrapWithUserInfo(List<TblComment> cs, String auth) {
        try {
            if (cs != null) {
                Map<TblComment, Future<String>> map = new HashMap<>();
                for (TblComment c : cs) {
                    Future<String> futureStr = fetchUserService.getUserInfoAsync(auth, c.getUserId());
                    if (futureStr != null) {
                        map.put(c, futureStr);
                    }
                }
                Iterator iter = map.entrySet().iterator();

                while (iter.hasNext()) {
                    Map.Entry<TblComment, Future<String>> entry = (Map.Entry) iter.next();
                    TblComment comment = entry.getKey();
                    Future<String> futureStr = entry.getValue();
                    String userStr = futureStr.get(1000, TimeUnit.MILLISECONDS);
                    JSONObject user = JSONObject.parseObject(userStr);
                    if (user != null && user.get("uname") != null) {
                        comment.setUname(user.get("uname").toString());
                    }
                }
                iter = null;
                map = null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        return cs;
    }

    @Override
    public void likeComment(String commentId, String userId) {
        TblComment cmt = tblCommentDao.findById(commentId).orElse(null);

        if (cmt != null) {
            cmt.setLikeCount(cmt.getLikeCount() + 1);
        } else {
            throw new RuntimeException("没有该评论! commentId: " + commentId);
        }
    }

    @Override
    public void dislikeComment(String commentId, String userId) {
        TblComment cmt = tblCommentDao.findById(commentId).orElse(null);

        if (cmt != null) {
            cmt.setDislikeCount(cmt.getDislikeCount() + 1);
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
        comment.setUuid(UUID.randomUUID().toString());

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
