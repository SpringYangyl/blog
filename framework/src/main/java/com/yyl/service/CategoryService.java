package com.yyl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyl.domain.ResponseResult;
import com.yyl.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-02-13 20:25:23
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
