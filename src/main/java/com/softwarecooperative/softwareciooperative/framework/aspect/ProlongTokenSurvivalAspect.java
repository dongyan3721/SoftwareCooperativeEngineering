package com.softwarecooperative.softwareciooperative.framework.aspect;


import com.softwarecooperative.softwareciooperative.framework.exception.GeneralServiceException;
import com.softwarecooperative.softwareciooperative.framework.exception.UnAuthenticatedException;
import com.softwarecooperative.softwareciooperative.framework.net.CustomHttpStatus;
import com.softwarecooperative.softwareciooperative.framework.net.HttpStatus;
import com.softwarecooperative.softwareciooperative.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/2/29-16:55:28
 */
@Aspect
@Component
public class ProlongTokenSurvivalAspect {

    @Pointcut("@annotation(com.softwarecooperative.softwareciooperative.framework.annotation.CheckTokenValidityAndSurvival)")
    public void tokenVerification() {}

    @Around("tokenVerification()")
    public Object excludeUnAuthenticatedCases(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader(JwtUtils.TOKEN);
        if(token==null){
            throw new GeneralServiceException("Unauthorized!", HttpStatus.UNAUTHORIZED);
        }
        try {
            JwtUtils.verify(token);
        } catch (UnAuthenticatedException e) {
            throw new GeneralServiceException("Invalid token!", HttpStatus.UNAUTHORIZED);
        }
        if(!JwtUtils.isNeedUpdate(token)){
            throw new GeneralServiceException("No need to flush", CustomHttpStatus.NO_NEED_TO_FLUSH_TOKEN);
        }
        return joinPoint.proceed();
    }
}
