package com.yyl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyl.domain.ResponseResult;
import com.yyl.domain.vo.CommentVo;
import com.yyl.domain.vo.PageVo;
import com.yyl.entity.Comment;
import com.yyl.enums.AppHttpCodeEnum;
import com.yyl.exception.SystemException;
import com.yyl.mapper.CommentMapper;
import com.yyl.service.CommentService;
import com.yyl.service.UserService;
import com.yyl.utils.BeanCopyUtils;
import com.yyl.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-02-14 18:24:43
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    UserService userService;
    @Override
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章下的zi评论
        //根评论  rootid = -1
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getArticleId,articleId);
        wrapper.eq(Comment::getRootId,-1);
        wrapper.orderByDesc(Comment::getCreateTime);
        Page<Comment> page = new Page<>(pageNum,pageSize);
        Page<Comment> commentPage = page(page, wrapper);
        List<Comment> records = commentPage.getRecords();
        List<CommentVo> commentVos = toCommentVoList(records);
        for (CommentVo commentVo : commentVos) {
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        }
        return ResponseResult.okResult(new PageVo(commentVos,page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        //从SecurityContextHolder获取token 从而获取用户信息
        save(comment);
        return null;
    }

    private List<CommentVo> getChildren(Long id) {
        //查找子评论
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getId,id);
        List<Comment> list = list(wrapper);
        List<CommentVo> commentVoList = toCommentVoList(list);
        return commentVoList;
    }

    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历vo集合
        for (CommentVo commentVo : commentVos) {
            //通过creatyBy查询用户的昵称并赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            //通过toCommentUserId查询用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            if(commentVo.getToCommentUserId()!=-1){
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }

}
