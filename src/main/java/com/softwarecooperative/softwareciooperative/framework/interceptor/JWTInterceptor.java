package com.softwarecooperative.softwareciooperative.framework.interceptor;

import com.softwarecooperative.softwareciooperative.framework.context.BaseContext;
import com.softwarecooperative.softwareciooperative.framework.exception.UnAuthenticatedException;
import com.softwarecooperative.softwareciooperative.framework.net.HttpStatus;
import com.softwarecooperative.softwareciooperative.utils.JwtUtils;
import com.softwarecooperative.softwareciooperative.framework.annotation.AccessWithoutVerification;
import com.softwarecooperative.softwareciooperative.framework.exception.GeneralServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/3/4-18:44:32
 */
public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        if(handler instanceof HandlerMethod){
            AccessWithoutVerification annotation = ((HandlerMethod) handler).getMethodAnnotation(AccessWithoutVerification.class);
            if(annotation!=null){
                return true;
            }
        }

        String userId;
        try {
            userId = JwtUtils.verify(token);
            BaseContext.setCurrentId(userId);
        } catch (UnAuthenticatedException e) {
            throw new GeneralServiceException("Time Expired!", HttpStatus.FORBIDDEN);
        }


        return true;
    }
}
