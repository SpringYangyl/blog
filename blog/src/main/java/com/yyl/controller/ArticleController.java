package com.yyl.controller;

import com.yyl.annotation.SysLog;
import com.yyl.domain.Event;
import com.yyl.domain.ResponseResult;
import com.yyl.entity.Article;
import com.yyl.entity.LoginUser;
import com.yyl.service.ArticleService;
import com.yyl.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    /**
     *
     * @return
     */
    @SysLog(bussinessName = "hotArticleList")
    @GetMapping("hotArticleList")
    public ResponseResult hotArticleList(){
        //查询文章 封装结果

        ResponseResult result =  articleService.hotArticleList();
        return result;
    }

    /**
     *
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    @GetMapping("/articleList")
    @SysLog(bussinessName = "articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @SysLog(bussinessName = "getArticleDetail")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

    /**
     *
     * @param article
     * @return
     */
    @PostMapping("addArticle")
    @Transactional
    @SysLog(bussinessName = "addArticle")
    public ResponseResult addArticle(@RequestBody Article article){

        return articleService.addArticle(article);
    }

    /**
     *
     * @param article
     * @return
     */
    @PostMapping("updateArticle")
    @SysLog(bussinessName = "updateArticle")
    @Transactional
    public ResponseResult updateArticle(@RequestBody Article article){
        return articleService.updateArticle(article);
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("delete/{id}")
    @Transactional
    @SysLog(bussinessName = "deleteArticle")
    public ResponseResult deleteArticle(@PathVariable("id") Long id){
        return articleService.deleteArticle(id);
    }

    /**
     * 根据文章id查询点赞数
     * @param ArticleId
     * @return
     */
    @GetMapping("getLike/{ArticleId}")
    @SysLog(bussinessName = "getLike")
    public ResponseResult getLike(@PathVariable("ArticleId") Long ArticleId){
        return articleService.getLike(ArticleId);
    }

    /**
     * 查询该用户是否给这篇文章点赞
     * @param ArticleId
     * @param UserId
     * @return
     */
    @GetMapping("getEitherLike/{ArticleId}/{UserId}")
    @SysLog(bussinessName = "getEitherLike")
    public ResponseResult getEitherLike(@PathVariable("ArticleId") Long ArticleId,@PathVariable("UserId") Long UserId){
        return articleService.getEitherLike(ArticleId,UserId);
    }
    @GetMapping("demo")
    public ResponseResult demo(){
        Authentication authentication = SecurityUtils.getAuthentication();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = SecurityUtils.getUserId();
        HashMap<String,Object> map = new HashMap<>();
        map.put("authentication",authentication);
        map.put("loginUser",loginUser);
        map.put("userId",userId);
        return ResponseResult.okResult(map);
    }
}
