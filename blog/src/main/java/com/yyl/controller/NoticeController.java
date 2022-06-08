package com.yyl.controller;

import com.yyl.domain.ResponseResult;
import com.yyl.entity.Article;
import com.yyl.entity.User;
import com.yyl.service.ArticleService;
import com.yyl.service.UserService;
import com.yyl.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("notice")
public class NoticeController {
    @Autowired
    RedisCache redisCache;
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @GetMapping("getNotice")
    public ResponseResult getNotice(){
        Map<String, Object> like = redisCache.getCacheMap("like1");
        Map<String,Object> comment = redisCache.getCacheMap("comment1");
        Map<String, List<String>> res = new HashMap<>();
        ArrayList<String> longs = new ArrayList<>();
        ArrayList<String> com = new ArrayList<>();
        like.forEach((k,v)->{
            String[] split = k.split(":");
            User user = userService.getById(split[0]);
            Article article = articleService.getById(split[1]);
            String str = "用户"+":"+user.getNickName()+","+"给你的文章"+"《"+article.getTitle()+"》"+"点赞了";
            longs.add(str);
            redisCache.delCacheMapValue("like1",k);
        });
        comment.forEach((k,v)->{
            String[] split = k.split(":");
            User user = userService.getById(split[0]);
            Article article = articleService.getById(split[1]);
            String str = "用户"+":"+user.getNickName()+","+"给你的文章"+"《"+article.getTitle()+"》"+"评论了";
            com.add(str);
            redisCache.delCacheMapValue("comment1",k);
        });
        res.put("like",longs);
        res.put("comment",com);
        return ResponseResult.okResult(res);
    }
    @GetMapping("getNum")
    public ResponseResult getNum(){
        Map<String, Object> like = redisCache.getCacheMap("like1");
        Map<String,Object> comment = redisCache.getCacheMap("comment1");
        int likeCount = 0;
        if(like != null){
            likeCount = like.size();
        }
        int commentCount = 0;
        if(comment != null){
            commentCount = comment.size();
        }
        return ResponseResult.okResult(likeCount+commentCount);
    }
}
