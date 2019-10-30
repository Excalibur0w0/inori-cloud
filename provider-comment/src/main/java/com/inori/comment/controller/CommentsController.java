package com.inori.comment.controller;

import com.inori.comment.pojo.TblComment;
import com.inori.comment.service.CommentService;
import com.inori.comment.service.FetchUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comments")
public class CommentsController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<TblComment> findAllBySongId(@RequestHeader("Authorization")String authorization,
                                            @RequestParam("songId")String songId) {
        // 排序的方式（注意）
        return commentService.wrapWithUserInfo(commentService.getAllBySongId(songId), authorization);
    }

    @GetMapping("hot")
    public List<TblComment> findHotBySongId(@RequestParam("songId")String songId) {
        return null;
    }
}
