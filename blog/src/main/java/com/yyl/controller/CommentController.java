package com.yyl.controller;

import com.yyl.domain.ResponseResult;
import com.yyl.entity.Comment;
import com.yyl.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.commentList(articleId,pageNum,pageSize);
    }
    @PostMapping("/addComment")
    public ResponseResult addComment(@RequestBody Comment comment){
        return  commentService.addComment(comment);
    }

}