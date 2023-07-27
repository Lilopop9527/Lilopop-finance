package com.auth.section;

import com.common.core.pojo.LogMessage;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

@Component
@Aspect
public class ServiceSection {
    @Autowired
    private StreamBridge streamBridge;

    @Pointcut("execution(public * com.auth.service..*.*(..))")
    public void sectionPoint(){}

    @Before("sectionPoint()")
    public void beforePoint(JoinPoint point){
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        StringBuffer bu = new StringBuffer(method.getDeclaringClass().toString());
        bu.append(".").append(method.getName());
        String[] names = signature.getParameterNames();
        Object[] values = point.getArgs();
        String data = bu.toString();
        bu.setLength(0);
        for (int i = 0; i < names.length; i++) {
            bu.append(names[i]).append(":").append(values[i]).append(" ");
        }
        LogMessage msg = LogMessage.builder("auth", bu.toString(), 0, data);
        streamBridge.send("logSend", msg);
    }

    @AfterReturning(pointcut = "sectionPoint()",returning = "o")
    public void afterPoint(JoinPoint point,Object o){
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        StringBuffer bu = new StringBuffer(method.getDeclaringClass().toString());
        bu.append(".").append(method.getName());
        LogMessage msg = LogMessage.builder("auth", Objects.isNull(o)?"无返回值":o.toString(), 1, bu.toString());
        streamBridge.send("logSend", msg);
    }

    @AfterThrowing(pointcut = "sectionPoint()",throwing = "e")
    public void exceptionPoint(JoinPoint point,Exception e){
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        StringBuffer bu = new StringBuffer(method.getDeclaringClass().toString());
        bu.append(".").append(method.getName());
        String[] names = signature.getParameterNames();
        Object[] values = point.getArgs();
        String data = bu.toString();
        bu.setLength(0);
        for (int i = 0; i < names.length; i++) {
            bu.append(names[i]).append(":").append(values[i]).append(" ");
        }
        bu.append(",异常为：").append(e);
        LogMessage msg =LogMessage.builder("auth", bu.toString(), 2,data);
        streamBridge.send("logSend", msg);
    }
}
