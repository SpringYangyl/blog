package com.yyl.filter;

import com.alibaba.fastjson.JSON;
import com.yyl.domain.ResponseResult;
import com.yyl.entity.LoginUser;
import com.yyl.enums.AppHttpCodeEnum;
import com.yyl.utils.JwtUtil;
import com.yyl.utils.RedisCache;
import com.yyl.utils.WebUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
//        String method = httpServletRequest.getMethod();
//        if ("OPTIONS".equals(method)) {
//            filterChain.doFilter(httpServletRequest,httpServletResponse);
//            return ;
//        }

        String token = httpServletRequest.getHeader("token");
        if(!StringUtils.hasText(token)){
            //说明该接口不用登录  直接放行
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }
        String id = claims.getSubject();
        LoginUser cacheObject = redisCache.getCacheObject("bloglogin" + id);
        if(Objects.isNull(cacheObject)){
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }
        //解析获取user
        //从redis中获取信息   存入securitcontexholder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(cacheObject,null,null);
        log.error("这是存入的认证信息{}",authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
