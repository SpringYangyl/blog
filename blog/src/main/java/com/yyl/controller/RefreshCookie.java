package com.yyl.controller;

import com.yyl.domain.ResponseResult;
import com.yyl.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("refresh")
public class RefreshCookie {
    @Autowired
    private RedisCache redisCache;
    @GetMapping("cookie")
    public ResponseResult refreshCookie(){
        String token = redisCache.getCacheObject("token");
        return ResponseResult.okResult(token);
    }
}
