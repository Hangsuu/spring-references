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
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtLoginServiceImpl implements JwtLoginService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisRepository redisRepository;

    @Override
    public JwtTokenVO login(MemberVO vo) {
        log.info("MemberVO = {}", vo.getId());
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        vo.getRoles().add("client");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(vo.getId(), null, vo.getAuthorities());

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtTokenVO jwtTokenVO = jwtTokenProvider.generateToken(authenticationToken);

        // 4. redis에 저장
        log.info("request username = {}, password = {}", vo.getId(), vo.getPassword());
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtTokenVO.getAccessToken(), jwtTokenVO.getRefreshToken());
        redisRepository.deleteById("accessToken" + vo.getId());
        redisRepository.deleteById("refreshToken" + vo.getId());
        redisRepository.save(new MemberRedisEntity("accessToken" + vo.getId() ,jwtTokenVO.getAccessToken()));
        redisRepository.save(new MemberRedisEntity("refreshToken" + vo.getId(), jwtTokenVO.getRefreshToken()));

        return jwtTokenVO;
    }

    @Override
    public void logout(MemberVO vo) {

    }

    @Override
    public JwtTokenVO requestToken(String accessToken) {
        // access 토큰이 유효하지 않은 경우
        String id = jwtTokenProvider.getIdFromJwtToken(accessToken);
        //refresh 토큰 체크
        Optional<MemberRedisEntity> byId = redisRepository.findById("refreshToken" + id);
        JwtTokenVO jwtTokenVO = null;

        String refreshToken = "";
        if(byId.isEmpty()) {
            // 리프레시 토큰이 유효하지 않다면 access, refresh 토큰 기록 삭제
            redisRepository.deleteById("accessToken" + id);
            redisRepository.deleteById("refreshToken" + id);
        } else {
            refreshToken = redisRepository.findById("refreshToken" + id).get().getJwtToken();
            log.info("refreshToken = {}", refreshToken);
            boolean isRefreshTokenValidate = jwtTokenProvider.validateToken(refreshToken);
            if(isRefreshTokenValidate) {
                log.info("리프레시 토큰 재발급");
                // 리스레시 토큰이 유효하다면 access, refresh token renew
                Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
                jwtTokenVO = jwtTokenProvider.generateToken(authentication);

                redisRepository.deleteById("accessToken" + id);
                redisRepository.deleteById("refreshToken" + id);
                redisRepository.save(new MemberRedisEntity("accessToken" + id ,jwtTokenVO.getAccessToken()));
                redisRepository.save(new MemberRedisEntity("refreshToken" + id, jwtTokenVO.getAccessToken()));

            } else {
                // 리프레시 토큰이 유효하지 않다면 access, refresh 토큰 기록 삭제
                redisRepository.deleteById("accessToken" + id);
                redisRepository.deleteById("refreshToken" + id);
            }
        }
        return jwtTokenVO;
    }
}
