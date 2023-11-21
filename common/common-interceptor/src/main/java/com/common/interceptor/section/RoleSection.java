package com.common.interceptor.section;

import com.common.core.exception.Asserts;
import com.common.interceptor.annotation.CheckRole;
import com.common.interceptor.config.ThreadInfo;
import jakarta.security.auth.message.AuthException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author: Lilopop
 * @description:
 */
@Component("roleSection")
@Aspect
public class RoleSection {

    @Pointcut("execution(public **(..))")
    public void pointCut(){}

    @Before("@annotation(com.common.interceptor.annotation.CheckRole)")
    public void beforeCheck(JoinPoint point) throws AuthException {
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        CheckRole annotation = method.getAnnotation(CheckRole.class);
        Map<String,Object> info = ThreadInfo.get();
        Integer roles = (Integer) info.get("role");
        int values = annotation.value();
        if (values>roles)
            throw new AuthException("权限错误");
    }
}
