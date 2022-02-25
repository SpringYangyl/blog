package com.yyl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyl.domain.ResponseResult;
import com.yyl.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-02-14 13:35:59
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
