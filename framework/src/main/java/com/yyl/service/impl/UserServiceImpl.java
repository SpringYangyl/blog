package com.yyl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyl.entity.User;
import com.yyl.mapper.UserMapper;
import com.yyl.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-02-14 18:54:57
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
