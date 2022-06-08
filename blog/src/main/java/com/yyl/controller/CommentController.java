package com.yyl.controller;

import com.yyl.annotation.SysLog;
import com.yyl.domain.Event;
import com.yyl.domain.ResponseResult;
import com.yyl.entity.Comment;
import com.yyl.kafka.Producer;
import com.yyl.service.CommentService;
import com.yyl.utils.SecurityUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    Producer producer;

    /**
     *
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @SysLog(bussinessName = "commentList")
    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.commentList("0",articleId,pageNum,pageSize);
    }

    /**
     *
     * @param comment
     * @return
     */
    @PostMapping("/addComment")
    @SysLog(bussinessName = "addComment")
    @Transactional
    public ResponseResult addComment(@RequestBody Comment comment){
        Long userId = SecurityUtils.getUserId();
        Event event = new Event("comment1", userId, "评论", comment.getArticleId(), "我给你评论了");
        producer.sent(event);
        return  commentService.addComment(comment);
    }

    /**
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/linkCommentList")
    @SysLog(bussinessName = "linkCommentList")
    public ResponseResult linkCommentList(@Param("pageNum") Integer pageNum,@Param("pageSize") Integer pageSize){
        return commentService.commentList("1",null,pageNum,pageSize);
    }

}