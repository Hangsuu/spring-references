package com.example.insiderback.common.security.config;

import com.example.insiderback.common.jwt.config.JwtAuthenticationFilter;
import com.example.insiderback.common.jwt.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // REST API이므로 basic auth 및 csrf 보안을 사용하지 않음
                .httpBasic(HttpBasicConfigurer::disable) // Basic 인증을 사용하지 않음
                .csrf(CsrfConfigurer::disable) //CSRF(Cross-Site Request Forgery) 보안을 비활성화
                // JWT를 사용하기 때문에 세션을 사용하지 않음
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> // 요청에 대한 인가 규칙 설정
                        authorize
                                .requestMatchers("/members/sign-in").permitAll() // "members/sign-in" 경로에 대한 요청은 모든 사용자에게 허용
                                .requestMatchers("/members/test").hasRole("USER") // "members/test" 경로에 대한 요청은 "USER" 권한을 가진 사용자만 허용
                                .anyRequest().authenticated() // 이 밖에 모든 요청에 대해서 인증을 필요로 한다는 설정
                )
                // JWT 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class).build();
    }

    /**
     * DelegatingPasswordEncoder를 생성하여 반환
     * DelegatingPasswordEncoder는 여러 암호화 알고리즘을 지원하며, Spring Security의 기본 권장 알고리즘을 사용하여 비밀번호를 인코딩
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt Encoder 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}