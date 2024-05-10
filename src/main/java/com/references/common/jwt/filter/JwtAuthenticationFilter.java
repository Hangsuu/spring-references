package com.references.common.jwt.filter;

import com.references.common.jwt.model.JwtData;
import com.references.common.jwt.service.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 클라이언트 요청 시 JWT 인증을 하기 위해 설치하는 커스텀 필터로, UsernamePasswordAuthenticationFilter 이전에 실행 할 것이다.
 * 클라이언트로부터 들어오는 요청에서 JWT 토큰을 처리하고, 유효한 토큰인 경우 해당 토큰의 인증 정보(Authentication)를
 * SecurityContext에 저장하여 인증된 요청을 처리할 수 있도록 한다.
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private static String baseUrl;

    private static final Set<String> approvedUrl = new HashSet<>(Arrays.asList("member/requestToken", "member/login"));

    @Value("${spring.data.rest.base-path}")
    public void setKey(String value) {
        baseUrl = value;
    }

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("requestURL = {}", request.getRequestURL());
        // 토큰 재발급 URL일 경우, 로그인 URL일 경우 접근 허용
        if(approvedUrl.contains(request.getRequestURL().toString().replaceAll(baseUrl + "rest/", "")) ||
                //rest api 접근이 아닐 경우(swagger-ui 등)
                !request.getRequestURL().toString().contains("rest")) {
            // 토큰이 없어도 접근 허용
            filterChain.doFilter(request, response);
            return;
        }

        // 1. Request Header에서 JWT 토큰 추출
        String headerAuthorization = ((HttpServletRequest) request).getHeader("Authorization");
        String token = "";
        if(headerAuthorization != null) {
            token = headerAuthorization.replaceAll(JwtData.BEARER.getName(), "");
        }

        log.info("token = {}", token);
        boolean isValidatedToken = false;
        boolean tokenIsNotEmpty = token != null && !token.equals("");
        if(tokenIsNotEmpty) {
            try {
                isValidatedToken = jwtTokenProvider.validateToken(token);
            } catch (RuntimeException e) {
                // 만료된 토큰일 경우 403 Forbidden 에러를 발생시킴
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            } catch (Exception e) {
                // 기타 예외가 발생한 경우 500 Internal Server Error를 발생시킴
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
        }

        // 2. validateToken으로 토큰 유효성 검사
        if (tokenIsNotEmpty && isValidatedToken) { // JwtTokenProvider의 validateToken() 메서드로 JWT 토큰의 유효성 검증
            // 토큰이 유효하면 JwtTokenProvider의 getAuthentication() 메서드로 Authentication(인증 객체) 객체를 가지고 와서 SecurityContext에 저장
            // 요청을 처리하는 동안 인증 정보 유지
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            // 토큰이 없어도 접근 허용
            filterChain.doFilter(request, response);
            return;
        }
        // chain.doFilter()를 호출하여 다음 필터로 요청을 전달
        filterChain.doFilter(request, response);
    }
    /**
     *  "Bearer"는 토큰 유형이며, 실제 토큰이 뒤에 따라옵니다. 토큰 자체는 세 부분으로 구성되어 있으며, 각 부분은 마침표(.)로 구분되어 Base64Url로 인코딩되어 있습니다.
     * 헤더(Header): 토큰의 유형 및 사용되는 서명 알고리즘에 대한 정보를 포함합니다.
     * 페이로드(Payload): 클레임(주장)을 포함합니다. 클레임은 주로 사용자에 대한 정보 및 추가 데이터를 나타냅니다.
     * 서명(Signature): JWT의 전송자가 누구인지를 확인하는 데 사용됩니다.
     */
}