package com.yyl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyl.domain.ResponseResult;
import com.yyl.entity.User;
import com.yyl.entity.UserInfoVo;
import com.yyl.enums.AppHttpCodeEnum;
import com.yyl.exception.SystemException;
import com.yyl.mapper.UserMapper;
import com.yyl.service.UserService;
import com.yyl.utils.BeanCopyUtils;
import com.yyl.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-02-14 18:54:57
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseResult userInfo() {
        //获取当前用户的id
        Long userId = SecurityUtils.getUserId();
        User user = getById(userId);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        boolean b = updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
        }
        if(userExist(user.getUserName())){
            throw  new SystemException(AppHttpCodeEnum.NEED_LOGIN);
        }
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        save(user);
        return ResponseResult.okResult();
    }

    private boolean userExist(String userName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,userName);
        int count = count(wrapper);
        return count>0;
    }

}
