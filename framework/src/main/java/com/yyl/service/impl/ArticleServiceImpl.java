package com.yyl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyl.constants.SystemConstants;
import com.yyl.domain.ResponseResult;
import com.yyl.domain.vo.ArticleDetailVo;
import com.yyl.domain.vo.ArticleListVo;
import com.yyl.domain.vo.HotArticleVo;
import com.yyl.domain.vo.PageVo;
import com.yyl.entity.Article;
import com.yyl.entity.Category;
import com.yyl.mapper.ArticleMapper;
import com.yyl.service.ArticleService;
import com.yyl.service.CategoryService;
import com.yyl.service.RedisService;
import com.yyl.utils.BeanCopyUtils;
import com.yyl.utils.RedisKeyUtils;
import com.yyl.utils.SecurityUtils;
import io.jsonwebtoken.lang.Strings;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2022-02-13 18:54:15
 */
@Slf4j
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    CategoryService categoryService;
    @Autowired
    RedisService redisService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    HttpServletRequest request;
    @Override
    public ResponseResult hotArticleList() {
        //查询最热文章 浏览量最大的
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //按照浏览量排序  必须是正式文章 status =0
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        wrapper.orderByDesc(Article::getViewCount);
        Page<Article> page = new Page<>(1,10);
        Page<Article> res = page(page, wrapper);
        List<Article> records = res.getRecords();
        List<HotArticleVo> hotArticleVoList = BeanCopyUtils.copyBeanList(records, HotArticleVo.class);
        return ResponseResult.okResult(hotArticleVoList);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //wrapper.eq(Objects.nonNull(categoryId) && categoryId>0,Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        // 如果 有categoryId 就要 查询时要和传入的相同
        // 状态是正式发布的
        wrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        if(categoryId != 0){
            wrapper.eq(Article::getCategoryId,categoryId);
        }
        wrapper.orderByDesc(Article::getIsTop);
        // 对isTop进行降序
        Page<Article> page = new Page<>(pageNum,pageSize);
        //分页查询
        Page<Article> articlePage = page(page, wrapper);
        List<Article> records = articlePage.getRecords();
        //
        records.stream().map( article-> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(records, ArticleListVo.class);
        //查询分类名字

        PageVo pageVo  = new PageVo(articleListVos,articlePage.getTotal());
        return ResponseResult.okResult(pageVo);
        //封装查询结果
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        Article article = getById(id);
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category != null){
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
        }

    @Override
    public ResponseResult addArticle(Article article) {
        if(Objects.nonNull(article)){
            //.setCreateTime();
            //article.setCreateTime(new Date());
            save(article);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateArticle(Article article) {
        updateById(article);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        baseMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLike(Long articleId) {
        if(Objects.nonNull(redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,String.valueOf(articleId)))){
            Integer count = (Integer) redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, String.valueOf(articleId));
            redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,String.valueOf(articleId),count);
            return ResponseResult.okResult(count);
        }
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getId,articleId);
        Article one = getOne(wrapper);
        Long viewCount = one.getViewCount();
        Integer value = Integer.parseInt(String.valueOf(viewCount));
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,String.valueOf(articleId),value);
        return ResponseResult.okResult(value);
    }

    /**
     * 根据用户id和文章id查询是否点赞
     * @param articleId
     * @param userId
     * @return
     */
    @Override
    public ResponseResult getEitherLike(Long articleId, Long userId) {
        int value = redisService.getEitherLike(articleId,userId);
        return ResponseResult.okResult(value);
    }

    /**
            * 获取所有文章的点赞数 然后更新
     */
    @Override
    public void updateViewCount() {
        HashMap<Long,Long> map = redisService.getLikedDataFromRedis();
        if(map.size() != 0){
            map.forEach((k,v)->{
                Article article = new Article();
                article.setId(k);
                article.setViewCount(v);
                baseMapper.updateById(article);
            });
        }
    }

    @Override
    public ResponseResult demo(Long id) {
        Article article = new Article();
        article.setId(id);
        article.setViewCount(10L);
        updateById(article);
        String token = request.getHeader("token");
        log.info("token{}",token);
        Long userId = SecurityUtils.getUserId();

        return ResponseResult.okResult(userId);
    }


}

