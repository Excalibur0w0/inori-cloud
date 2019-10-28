package com.inori.comment.controller;

import com.inori.comment.pojo.TblComment;
import com.inori.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comments")
public class CommentsController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<TblComment> findAllBySongId(@RequestParam("songId")String songId) {
        // 排序的方式（注意）
        return commentService.getAllBySongId(songId);
    }
}
