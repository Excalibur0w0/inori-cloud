package com.inori.comment.controller;

import com.inori.comment.client.UserServiceClient;
import com.inori.comment.dao.TblCommentDao;
import com.inori.comment.pojo.TblComment;
import com.inori.comment.service.CommentService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserServiceClient client;

    @GetMapping
    public TblComment find(@RequestParam("commentId") String commentId) {
        return commentService.getById(commentId);
    }

    @DeleteMapping
    public boolean delete(@RequestParam("commentId") String commentId) {
        commentService.deleteById(commentId);
        return true;
    }

    @PostMapping
    public TblComment create(@RequestParam("content") String content,
                             @RequestParam("songId") String songId,
                             @RequestHeader("Authorization") String authorization) {
        String userId = client.getUserInfoByToken(authorization);
        return commentService.makeComment(content, songId, userId);
    }
}
