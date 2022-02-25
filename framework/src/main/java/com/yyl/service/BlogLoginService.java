package com.yyl.service;

import com.yyl.domain.ResponseResult;
import com.yyl.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
