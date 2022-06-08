package com.yyl.service;

import com.yyl.domain.ArticleLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface LikedService {

    /**
     * 保存点赞记录
     * @param articleLike
     * @return
     */
    ArticleLike save(ArticleLike articleLike);

    /**
     * 批量保存或修改
     * @param list
     */
    List<ArticleLike> saveAll(List<ArticleLike> list);


    /**
     * 根据被点赞人的id查询点赞列表（即查询都谁给这个pianwenzhang点赞过）
     * @param ArticleId 被点赞人的id
     * @param pageable
     * @return
     */
    Page<ArticleLike> getLikedListByLikedUserId(String ArticleId, Pageable pageable);

    /**
     * 根据点赞人的id查询点赞列表（即查询这个人都给谁点赞过）
     * @param UserId
     * @param pageable
     * @return
     */
    Page<ArticleLike> getLikedListByLikedPostId(String UserId, Pageable pageable);

    /**
     * 通过被点赞人和点赞人id查询是否存在点赞记录
     * @param ArticleId
     * @param UserId
     * @return
     */
    ArticleLike getByLikedUserIdAndLikedPostId(String ArticleId, String UserId);

    /**
     * 将Redis里的点赞数据存入数据库中
     */
    void transLikedFromRedis2DB();

    /**
     * 将Redis中的点赞数量数据存入数据库
     */
    void transLikedCountFromRedis2DB();

}