package com.example.insiderback.member.service.impl;

import com.example.insiderback.common.jwt.model.JwtTokenVO;
import com.example.insiderback.common.jwt.service.JwtTokenProvider;
import com.example.insiderback.common.redis.entity.MemberRedisEntity;
import com.example.insiderback.common.redis.repository.RedisRepository;
import com.example.insiderback.member.model.MemberVO;
import com.example.insiderback.member.repository.MemoryRepo;
import com.example.insiderback.member.service.JwtLoginService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtLoginServiceImpl implements JwtLoginService {
    private final MemoryRepo memoryRepo;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisRepository redisRepository;

    @Override
    public JwtTokenVO login(MemberVO vo) {
        log.info("MemberVO = {}", vo.getId());
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(vo.getId(), vo.getPassword());

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtTokenVO jwtToken = jwtTokenProvider.generateToken(authentication);

        memoryRepo.setUser(vo);

        // 4. redis에 저장
        log.info("request username = {}, password = {}", vo.getId(), vo.getPassword());
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        redisRepository.save(new MemberRedisEntity("accessToken" + jwtToken.getAccessToken(), vo));
        redisRepository.save(new MemberRedisEntity("refreshToken" + jwtToken.getAccessToken(), vo));

        // jwt 복호화 확인
//        String token = (redisRepository.findById(vo.getId()).get()).getJwtTokenVO().getAccessToken();
//        Jws<Claims> claims = jwtTokenProvider.getClaims(token);
//        log.info("grantType = {},",claims.getBody().get("grantType"));
//        log.info("accessToken = {},",claims.getBody().get("accessToken"));
//        log.info("refreshToken = {},",claims.getBody().get("refreshToken"));


        return jwtToken;
    }
}
