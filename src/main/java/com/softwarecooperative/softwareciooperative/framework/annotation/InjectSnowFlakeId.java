package com.softwarecooperative.softwareciooperative.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 加在mapper接口上，自动注入雪花id
 * @Author 嘉然今天吃向晚
 * @Date 2024/4/27-02:04:38
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectSnowFlakeId {
}
