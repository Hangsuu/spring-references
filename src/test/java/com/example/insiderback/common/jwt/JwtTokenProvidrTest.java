package com.example.insiderback.common.jwt;

import com.example.insiderback.common.jwt.model.JwtTokenVO;
import com.example.insiderback.common.jwt.service.JwtTokenProvider;
import com.example.insiderback.member.model.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Slf4j
@SpringBootTest
public class JwtTokenProvidrTest {
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    void generateTokenTest() {
        MemberVO memberVO = new MemberVO();
        memberVO.setId("test");

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberVO.getId(), null, memberVO.getAuthorities());

        JwtTokenVO jwtTokenVO = jwtTokenProvider.generateToken(authenticationToken);

        log.info("accessToken = {}", jwtTokenVO.getAccessToken());
        log.info("refreshToken = {}", jwtTokenVO.getRefreshToken());
        Assertions.assertThat(jwtTokenVO.getAccessToken()).isNotNull();
        Assertions.assertThat(jwtTokenVO.getRefreshToken()).isNotNull();
    }

    @Test
    void validateTokenTest() {
        MemberVO memberVO = new MemberVO();
        memberVO.setId("test");

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberVO.getId(), null, memberVO.getAuthorities());

        JwtTokenVO jwtTokenVO = jwtTokenProvider.generateToken(authenticationToken);

        Assertions.assertThat(jwtTokenProvider.validateToken(jwtTokenVO.getAccessToken())).isTrue();
        Assertions.assertThatThrownBy(() -> jwtTokenProvider.validateToken("wrongToken")).isInstanceOf(RuntimeException.class);
    }
}
