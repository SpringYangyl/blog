package com.yyl.handler.security;

import com.alibaba.fastjson.JSON;
import com.yyl.domain.ResponseResult;
import com.yyl.enums.AppHttpCodeEnum;
import com.yyl.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //认证失败后
        ResponseResult result = null;
        e.printStackTrace();
        if(e instanceof BadCredentialsException){
             result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),e.getMessage());

        }else if(e instanceof InsufficientAuthenticationException){
            result= ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else{
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,"认证或授权失败");
        }
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));

    }
}
