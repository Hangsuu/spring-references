package com.example.insiderback.member.service;

import com.example.insiderback.member.repository.MemberMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginServiceTest {
    @Autowired
    private MemberMapper memberMapper;

    @Test
    void selectOne() {
        int one = memberMapper.selectOne();

        Assertions.assertThat(one).isEqualTo(1);
    }

}
