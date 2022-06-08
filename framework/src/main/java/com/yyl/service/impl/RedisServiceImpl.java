package com.yyl.service.impl;

import com.yyl.domain.ResponseResult;
import com.yyl.service.ArticleService;
import com.yyl.service.RedisService;
import com.yyl.utils.RedisKeyUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.*;
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ArticleService articleService;
    @Override
    public void saveLiked2Redis(Long ArticleId, Long UserId) {
        String key = RedisKeyUtils.getLikedKey(ArticleId,UserId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED,key,1);
    }

    @Override
    public void unlikeFromRedis(Long ArticleId, Long UserId) {
        String key = RedisKeyUtils.getLikedKey(ArticleId,UserId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED,key,0);
    }

    @Override
    public void deleteLikedFromRedis(Long ArticleId, Long UserId) {
        String key = RedisKeyUtils.getLikedKey(ArticleId,UserId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED,key);
    }
    //
    @Override
    public void incrementLikedCount(Long ArticleId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,String.valueOf(ArticleId),1);
    }

    @Override
    public void decrementLikedCount(Long ArticleId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,String.valueOf(ArticleId),-1);
    }

    /**
     * 获取所有文章的点赞数
     * @return
     */
    @Override
    public HashMap<Long, Long> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object,Object>> scan = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, ScanOptions.NONE);
        HashMap<Long,Long> map = new HashMap<>();
        log.info("是不是null");
        while(scan.hasNext()){
            Map.Entry<Object, Object> next = scan.next();
            String  ArticleId = (String) next.getKey();
            Long id = Long.valueOf(ArticleId);
            Integer value1 = (Integer) next.getValue();
            Long value = value1.longValue();
            map.put(id,value);
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,ArticleId);
        }
        return map;
    }

    /**
     * 从数据库中查询原有的点赞数，然后显示的点赞数= 原有的+redis中的
     * 写一个获取某篇文章点赞数的方法
     *
     * @param articleId
     * @param userId
     * @return
     */
    @Override
    public ResponseResult like(Long articleId, Long userId) {
        saveLiked2Redis(articleId,userId);
        incrementLikedCount(articleId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult unlike(Long articleId, Long userId) {
        unlikeFromRedis(articleId,userId);
        decrementLikedCount(articleId);
        return ResponseResult.okResult();
    }

    /**
     * 获取某篇文章中的点赞数
     * @param articleId
     * @return
     */
    @Override
    public int getLike(Long articleId) {
        Integer integer = (Integer) redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, String.valueOf(articleId));
        if(Objects.nonNull(integer)){
            return integer;
        }
        return 0;
    }

    @Override
    public int getEitherLike(Long articleId, Long userId) {
        String key = RedisKeyUtils.getLikedKey(articleId,userId);
        Integer value = (Integer) redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
        if(Objects.isNull(value)){
            return 0;
        }
        return value;
    }



}
