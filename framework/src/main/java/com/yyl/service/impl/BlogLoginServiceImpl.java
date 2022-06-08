package com.yyl.service.impl;

import com.yyl.domain.ResponseResult;
import com.yyl.domain.vo.BlogUserLoginVo;
import com.yyl.entity.LoginUser;
import com.yyl.entity.User;
import com.yyl.entity.UserInfoVo;
import com.yyl.service.BlogLoginService;
import com.yyl.utils.BeanCopyUtils;
import com.yyl.utils.JwtUtil;
import com.yyl.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        //UserDetail   UserDetailService
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        //ProviderManger 认证  从userdetail service返回user信息
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名");
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(id);
        redisCache.setCacheObject("bloglogin" + id, loginUser);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        redisCache.setCacheObject("token", jwt);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);
    }

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //获取token 解析userid
        //删除redis中的信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long id = loginUser.getUser().getId();
        redisCache.deleteObject("bloglogin" + id);
        return ResponseResult.okResult();
    }
}
