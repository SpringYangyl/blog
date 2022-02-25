package com.yyl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyl.domain.ResponseResult;
import com.yyl.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-02-14 18:24:43
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
