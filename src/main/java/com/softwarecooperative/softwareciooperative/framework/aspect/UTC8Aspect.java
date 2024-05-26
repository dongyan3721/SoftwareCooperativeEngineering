package com.softwarecooperative.softwareciooperative.framework.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/26-02:29:39
 */
@Aspect
//@Component  // 需要后端+8h的时候将其启用
public class UTC8Aspect {

    @Pointcut("execution(* com.softwarecooperative.softwareciooperative.controller.*.*.*(..))")
    public void utcToUTC8() {}

    @Before("utcToUTC8()")
    public void utcToUTC8Aspect(JoinPoint joinPoint) throws IllegalAccessException {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg == null)
                continue;
            Class<?> argClazz = arg.getClass();
            for (Field field : argClazz.getDeclaredFields()) {
                if (!field.getType().equals(LocalDateTime.class))
                    continue;
                field.setAccessible(true);
                if (field.get(arg) == null)
                    continue;

                LocalDateTime dateTime = (LocalDateTime) field.get(arg);
                field.set(arg, dateTime.plusHours(8));
            }
        }
    }
}
