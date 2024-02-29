package com.example.insiderback;

import com.example.insiderback.member.model.MemberVO;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class TestTemplate {
    @BeforeEach
    void beforeEach() {
        log.info("각각의 테스트 전에 실행");
    }
    @AfterEach
    void afterEach() {
        log.info("각각의 테스트 후에 실행");
    }
    @Test
    @DisplayName("테스트명 입력")
    public void basicTest() {
        log.info("테스트 시작");
        String s = "";
        // 일치
        Assertions.assertThat(s).isEqualTo("");
        // null여부
        Assertions.assertThat(s).isNotNull();

        boolean b = true;
        // boolean 여부
        Assertions.assertThat(b).isTrue();
        org.junit.jupiter.api.Assertions.assertTrue(b);

        // Exception 발생 여부
        Assertions.assertThatThrownBy(() -> {throw new RuntimeException();}).isInstanceOf(RuntimeException.class);
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {throw new RuntimeException();});

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        // 리스트 포함 여부
        Assertions.assertThat(list).contains("1", "2");

        // 클래스가 일치하는지 판단
        Assertions.assertThat(new MemberVO()).isInstanceOf(MemberVO.class);

        log.info("테스트 끝");
    }

    @Autowired
    private Hello hello;
    // 테스트용 configuration 설정
    @TestConfiguration
    static class InitTxTestConfig {
        @Bean
        Hello hello() {
            return new Hello();
        }
    }

    @Slf4j
    static class Hello {
        @PostConstruct
        public static void hello() {
            log.info("TestConfiguration을 이용한 임시 빈 등록");
        }
    }
}
