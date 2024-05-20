package com.softwarecooperative.softwareciooperative.framework.interceptor;

import com.softwarecooperative.softwareciooperative.dao.mapper.TeacherMapper;
import com.softwarecooperative.softwareciooperative.framework.annotation.AccessWithoutVerification;
import com.softwarecooperative.softwareciooperative.framework.context.BaseContext;
import com.softwarecooperative.softwareciooperative.framework.exception.GeneralServiceException;
import com.softwarecooperative.softwareciooperative.framework.exception.service.IllegalOperationException;
import com.softwarecooperative.softwareciooperative.framework.net.StringConstant;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClass;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/20-19:23:52
 */

public class TeacherRoleInterceptor implements HandlerInterceptor {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod){
            AccessWithoutVerification annotation = ((HandlerMethod) handler).getMethodAnnotation(AccessWithoutVerification.class);
            if(annotation != null)
                return true;
        }

        Integer id = Integer.parseInt(BaseContext.getCurrentId());
        // 数据库中查询
        if (teacherMapper.ifExist(id))
            throw new IllegalOperationException(StringConstant.ILLEGAL_OPERATION);

        return true;
    }
}
