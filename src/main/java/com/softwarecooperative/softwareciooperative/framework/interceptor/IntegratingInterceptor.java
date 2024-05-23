package com.softwarecooperative.softwareciooperative.framework.interceptor;

import com.softwarecooperative.softwareciooperative.dao.mapper.ClassMapper;
import com.softwarecooperative.softwareciooperative.dao.mapper.StudentMapper;
import com.softwarecooperative.softwareciooperative.framework.annotation.AccessWithoutVerification;
import com.softwarecooperative.softwareciooperative.framework.context.BaseContext;
import com.softwarecooperative.softwareciooperative.framework.exception.service.GroupException;
import com.softwarecooperative.softwareciooperative.framework.net.StringConstant;
import com.softwarecooperative.softwareciooperative.pojo.entity.BClass;
import com.softwarecooperative.softwareciooperative.pojo.entity.BStudent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Description
 * @Author 嘉然今天吃向晚
 * @Date 2024/5/23-15:51:39
 */
public class IntegratingInterceptor implements HandlerInterceptor {

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){
            GetMapping getAnno = ((HandlerMethod) handler).getMethodAnnotation(GetMapping.class);
            if(getAnno != null)
                return true;
        }

        Integer stuId = Integer.parseInt(BaseContext.getCurrentId());
        BStudent stu = studentMapper.selectOne(BStudent.createIdQuery(stuId));
        if (!BClass.INTEGRATING.equals(classMapper.selectClassStatus(stu.getStudentClass())))
            throw new GroupException(StringConstant.NOT_IN_INTEGRATING_PHASE);

        return true;
    }
}
