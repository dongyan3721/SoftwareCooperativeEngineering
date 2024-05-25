package com.softwarecooperative.softwareciooperative.framework.annotation;

import java.lang.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/25-19:54:54
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClearAllCache {
    String[] value();
}
