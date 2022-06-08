package com.yyl.aspect;

import com.yyl.annotation.SysLog;
import com.yyl.entity.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Component
@Aspect
@Slf4j
public class LogAspect {
    //确定切点
    @Pointcut("@annotation(com.yyl.annotation.SysLog)")
    public void pt(){

    }
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {


        Object proceed;
        try{
            before(joinPoint);
            proceed = joinPoint.proceed();
            after(proceed);
            return proceed;
        }finally {
            log.info("=============END=============");
        }


    }

    private void after(Object proceed) {
        log.info("======Response======{}",proceed);
    }

    private void before(ProceedingJoinPoint joinPoint) {
      ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        SysLog logs = getSysLog(joinPoint);
        //LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("=========Start==========");
        log.info("=========Url=========={}",request.getRequestURI());
        //log.info("====调用的用户是===={}",user.getUser().getUserName());
        log.info("=========BussinessName=========={}",logs.bussinessName());
        log.info("=========Method=========={}",request.getMethod());

    }

    private SysLog getSysLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        SysLog annotation = signature.getMethod().getAnnotation(SysLog.class);
        return annotation;
    }
}
