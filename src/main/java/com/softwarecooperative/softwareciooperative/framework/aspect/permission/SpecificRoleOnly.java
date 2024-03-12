package com.softwarecooperative.softwareciooperative.framework.aspect.permission;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/3/11-11:57:23
 */
@Component
@Aspect
public class SpecificRoleOnly {


    @Pointcut("@annotation(com.softwarecooperative.softwareciooperative.framework.annotation.permission.SpecificRoleOnly)")
    public void checkRolePermission(){};



}
