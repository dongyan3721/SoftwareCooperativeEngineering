package com.softwarecooperative.softwareciooperative.framework.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class RequestLog {
    @Around("execution(* com.softwarecooperative.softwareciooperative.controller.*.*.*(..))")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String url = request.getRequestURI();
        String ip = request.getRemoteHost();
        int port = request.getRemotePort();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Parameter[] params = signature.getMethod().getParameters();
        Object[] values = joinPoint.getArgs();
        for (int i = 0; i < params.length; i++) {
            log.info(params[i] + ": "+ values[i]);
        }

        // 执行被通知的方法
        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long processingTime = endTime - startTime;

        log.info("{}:{} has requested {} using {}ms", ip, port, url, processingTime);

        return result;
    }
}
