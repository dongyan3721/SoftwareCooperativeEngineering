package com.softwarecooperative.softwareciooperative.framework.annotation.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识需要特定角色提交的功能
 * 比如小组作业阶段提交需要某一特定角色的经理
 * 如果是组内其他人期交不属于自己角色管理范畴的任务，报403
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpecificRoleOnly {
    
}
