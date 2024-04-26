package com.softwarecooperative.softwareciooperative.framework.aspect;

import com.softwarecooperative.softwareciooperative.framework.annotation.PrimaryKey;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClass;
import com.softwarecooperative.softwareciooperative.utils.snowflake.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @Description 自动注入雪花id
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/27-02:07:14
 */
@Aspect
@Component
@Slf4j
public class InjectPKAspect {

    /**
     * 切入点
     */
    @Pointcut("execution(* com.softwarecooperative.softwareciooperative.dao.mapper.*.*(..)) && " +
            "@annotation(com.softwarecooperative.softwareciooperative.framework.annotation.InjectSnowFlakeId)")
    public void autoInjectPKPointCut() {}

    /**
     * 前置通知，在通知中进行雪花id注入
     */
    @Before("autoInjectPKPointCut()")
    public void autoFill(JoinPoint joinPoint) {
//        log.info("正在注入雪花id...");
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0 || args[0] == null)
            return;

        Object entity = args[0];
        Class clazz = entity.getClass();
        Field pk = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> Arrays.asList(f.getAnnotations()).stream()
                        .anyMatch(a -> a.annotationType().equals(PrimaryKey.class)))
                .findFirst()
                .get();
        pk.setAccessible(true);
        try {
            pk.set(entity, SnowflakeIdWorker.nextId());
        } catch (IllegalAccessException e) {
            log.error("让我看看！");
            e.printStackTrace();
        }
    }
}
