package com.yyl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyl.constants.SystemConstants;
import com.yyl.domain.ResponseResult;
import com.yyl.domain.vo.CategoryVo;
import com.yyl.entity.Article;
import com.yyl.entity.Category;
import com.yyl.mapper.CategoryMapper;
import com.yyl.service.ArticleService;
import com.yyl.service.CategoryService;
import com.yyl.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-02-13 20:25:23
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    ArticleService articleService;
    /**
     * 1、查询文章表 状态为0 获取文章的分类id 并去重
     * 2、查询分类表
     * @return
     */
    @Override
    public ResponseResult getCategoryList() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> list = articleService.list(wrapper);
        Set<Long> collect = list.stream().map(o -> o.getCategoryId()).collect(Collectors.toSet());
        List<Category> categories = listByIds(collect);
        List<Category> categoryList = categories.stream().filter(o -> SystemConstants.STATUS_NROMAL.equals(o.getStatus())).collect(Collectors.toList());
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}
