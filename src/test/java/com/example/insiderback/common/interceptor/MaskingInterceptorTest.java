package com.example.insiderback.common.interceptor;

import com.example.insiderback.member.model.AnnotationTestVO;
import com.example.insiderback.member.repository.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MaskingInterceptorTest {
    @Autowired
    MemberMapper memberMapper;

    @Test
    public void maskingInterceptorTest() {
        AnnotationTestVO annotationTestVO = memberMapper.selectAnnotation();

        // 필들 타입 ID(3번째부터 마스킹처리)
        Assertions.assertThat(annotationTestVO.getNameValue()).isEqualTo("ab*");
    }
}
