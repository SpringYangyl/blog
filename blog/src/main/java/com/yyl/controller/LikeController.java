package com.yyl.controller;

import com.yyl.annotation.SysLog;
import com.yyl.domain.Event;
import com.yyl.domain.ResponseResult;
import com.yyl.kafka.Producer;
import com.yyl.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("like")
public class LikeController {
    @Autowired
    RedisService redisService;
    @Autowired
    Producer producer;

    /**
     *
     * @param ArticleId
     * @param UserId
     * @return
     */
    @PostMapping("/dolike/{ArticleId}/{UserId}")
    @Transactional
    @SysLog(bussinessName = "like")
    public ResponseResult like(@PathVariable("ArticleId") Long ArticleId,@PathVariable("UserId") Long UserId){
        Event event = new Event("like1", UserId, "点赞", ArticleId, "我给你点赞了");
        producer.sent(event);
        return redisService.like(ArticleId,UserId);
    }

    /**
     *
     * @param ArticleId
     * @param UserId
     * @return
     */
    @PostMapping("/unlike/{ArticleId}/{UserId}")
    @Transactional
    @SysLog(bussinessName = "unlike")
    public ResponseResult unlike(@PathVariable("ArticleId") Long ArticleId,@PathVariable("UserId") Long UserId){
        return redisService.unlike(ArticleId,UserId);
    }
}
