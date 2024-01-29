package com.example.insiderback.common.mybatis;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME) // 컴파일 이후에도 JVM이 계속 참조
@Inherited                          // 하위 클래스에도 적용 가능
@Documented                         // javadoc에 의해 문서화
@Target(ElementType.FIELD)          // 타입/필드 선언 시 어노테이션 사용(ElementType.Type)
public @interface MaskingField {
    MaskingData value();
}