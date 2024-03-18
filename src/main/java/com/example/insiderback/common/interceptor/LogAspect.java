package com.example.insiderback.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {
    @Around("@annotation(methodLog)")
    public Object doLog(ProceedingJoinPoint joinPoint, MethodLog methodLog) throws  Throwable {
        String description = methodLog.description();
        log.info("{} start : [{}]", description, joinPoint.getSignature());
        Object result = joinPoint.proceed();
        log.info("{} end : [{}]",description, joinPoint.getSignature());
        return result;
    }
}
