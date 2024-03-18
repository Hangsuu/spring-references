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
    @Around("@annotation(MethodLog)")
    public Object doLog(ProceedingJoinPoint joinPoint) throws  Throwable {
        log.info("method start : [{}]", joinPoint.getSignature());
        Object result = joinPoint.proceed();
        log.info("method end : [{}]", joinPoint.getSignature());
        return result;
    }
}
