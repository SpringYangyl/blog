package com.yyl.service;

import com.yyl.domain.ResponseResult;

import java.util.HashMap;

public interface RedisService {

    /**
     * 点赞。状态为1
     * @param ArticleId
     * @param UserId
     */
    void saveLiked2Redis(Long ArticleId, Long UserId);

    /**
     * 取消点赞。将状态改变为0
     * @param ArticleId
     * @param UserId
     */
    void unlikeFromRedis(Long ArticleId, Long UserId);

    /**
     * 从Redis中删除一条点赞数据
     * @param ArticleId
     * @param UserId
     */
    void deleteLikedFromRedis(Long ArticleId, Long UserId);

    /**
     * 该用户的点赞数加1
     * @param ArticleId
     */
    void incrementLikedCount(Long ArticleId);

    /**
     * 该用户的点赞数减1
     * @param ArticleId
     */
    void decrementLikedCount(Long ArticleId);

    /**
     * 获取Redis中存储的所有点赞数据
     * @return
     */
    HashMap<Long, Long> getLikedDataFromRedis();

    ResponseResult like(Long articleId, Long userId);

    ResponseResult unlike(Long articleId, Long userId);

    int getLike(Long articleId);

    int getEitherLike(Long articleId, Long userId);


    /**
     * 获取Redis中存储的所有点赞数量
     * @return
     */
    //List<LikedCountDTO> getLikedCountFromRedis();
}
