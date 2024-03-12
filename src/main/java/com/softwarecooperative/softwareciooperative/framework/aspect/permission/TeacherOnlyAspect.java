package com.softwarecooperative.softwareciooperative.framework.aspect.permission;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/3/11-11:48:38
 */
@Aspect
@Component
public class TeacherOnlyAspect {



    @Pointcut("@annotation(com.softwarecooperative.softwareciooperative.framework.annotation.permission.TeacherOnly)")
    public void teacherOnly(){}



    // TODO 验证token签名中角色字段，如果是老师或者管理员就放行，否则报403

}
