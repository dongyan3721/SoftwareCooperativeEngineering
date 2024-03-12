package com.softwarecooperative.softwareciooperative.framework.aspect.permission;

import com.softwarecooperative.softwareciooperative.framework.annotation.permission.SpecificRoleOnly;
import com.softwarecooperative.softwareciooperative.framework.exception.GeneralServiceException;
import com.softwarecooperative.softwareciooperative.framework.exception.UnAuthenticatedException;
import com.softwarecooperative.softwareciooperative.framework.net.HttpStatus;
import com.softwarecooperative.softwareciooperative.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/3/11-11:57:23
 */
@Component
@Aspect
public class SpecificRoleOnlyAspect {

//    @Resource
    // 似乎应单独设置老师表、管理员表
//    private StudentMapper studentMapper;


    @Pointcut("@annotation(com.softwarecooperative.softwareciooperative.framework.annotation.permission.SpecificRoleOnly)")
    public void checkRolePermission(){};


    @Before("checkRolePermission()")
    public void checkRolePermissionBefore(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        SpecificRoleOnly specificRoleOnly = signature.getMethod().getAnnotation(SpecificRoleOnly.class);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;

        String token = attributes.getRequest().getHeader(JwtUtils.TOKEN);
        try {
            String userId = JwtUtils.verify(token);
        } catch (UnAuthenticatedException e) {
            throw new GeneralServiceException("认证非法！", HttpStatus.UNAUTHORIZED);
        }

        // 根据token的签名的userId参数，判断当前角色是否有权限

        if (specificRoleOnly!=null){
            String role = specificRoleOnly.role();

            // 拿mapper查角色是否是学生而且要和这一步定义的role一样
            // Student studentRole = userMapper.selectStudentById(userId)
            // if (student==null) throw new GeneralServiceException("当前无权操作！", HttpStatus.FORBIDDEN);
            // if (!role.equals(student.getRole)) throw new GeneralServiceException("当前无权操作！", HttpStatus.FORBIDDEN);
            return;
        }
        throw new GeneralServiceException("当前无权操作！", HttpStatus.FORBIDDEN);
    }

}
