package com.softwarecooperative.softwareciooperative.framework.aspect;

import com.softwarecooperative.softwareciooperative.framework.annotation.ClearAllCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/25-19:53:35
 */
@Aspect
@Component
public class ClearAllCacheAspect {

    @Autowired
    private CacheManager cacheManager;

    @Pointcut("@annotation(com.softwarecooperative.softwareciooperative.framework.annotation.ClearAllCache)")
    public void clearAllCache() {}

    @After("clearAllCache()")
    public void manuallyClearAllCache(JoinPoint joinPoint) {
        MethodSignature sig = (MethodSignature) joinPoint.getSignature();
        ClearAllCache anno = sig.getMethod().getAnnotation(ClearAllCache.class);
        for (String s : anno.value()) {
            Cache cache = cacheManager.getCache(s);
            if (cache != null)
                cache.clear();
        }
    }
}
