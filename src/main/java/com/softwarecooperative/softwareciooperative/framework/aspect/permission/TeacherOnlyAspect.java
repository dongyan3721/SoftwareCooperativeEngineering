package com.softwarecooperative.softwareciooperative.framework.aspect.permission;

import com.softwarecooperative.softwareciooperative.dao.mapper.TeacherMapper;
import com.softwarecooperative.softwareciooperative.framework.context.BaseContext;
import com.softwarecooperative.softwareciooperative.framework.exception.service.IllegalOperationException;
import com.softwarecooperative.softwareciooperative.framework.net.StringConstant;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClass;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/3/11-11:48:38
 */
@Aspect
@Component
public class TeacherOnlyAspect {

    @Autowired
    private TeacherMapper teacherMapper;

    @Pointcut("@annotation(com.softwarecooperative.softwareciooperative.framework.annotation.permission.TeacherOnly)")
    public void teacherOnly(){}

    @Before("teacherOnly()")
    public void checkPermission(JoinPoint joinPoint) {
        Integer id = Integer.parseInt(BaseContext.getCurrentId());

        // 数据库中查询
        if (teacherMapper.ifExist(id))
            throw new IllegalOperationException(StringConstant.ILLEGAL_OPERATION);
    }

}
