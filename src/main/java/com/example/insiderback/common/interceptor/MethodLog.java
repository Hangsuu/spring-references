package com.example.insiderback.common.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 컴파일 이후에도 JVM이 계속 참조
@Target(ElementType.METHOD)          // 타입/필드 선언 시 어노테이션 사용(ElementType.Type)
public @interface MethodLog {
    String description() default "method" ;
}
