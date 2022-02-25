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
import com.yyl.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2022-02-13 18:54:15
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    CategoryService categoryService;
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
        wrapper.eq(Objects.nonNull(categoryId) && categoryId>0,Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        // 如果 有categoryId 就要 查询时要和传入的相同
        // 状态是正式发布的
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
}

