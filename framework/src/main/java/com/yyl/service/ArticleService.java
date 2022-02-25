package com.yyl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyl.domain.ResponseResult;
import com.yyl.entity.Article;

/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2022-02-13 18:54:15
 */
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);
}

