package com.example.insiderback.common.jwt.service;

import com.example.insiderback.common.jwt.model.JwtTokenModel;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Spring Security와 JWT 토큰을 사용하여 인증과 권한 부여를 처리하는 클래스이다.
 * 이 클래스에서 JWT 토큰의 생성, 복호화, 검증 기능을 구현하였다.
 */
@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;

    // application.yml에서 secret 값 가져와서 key에 저장
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Member 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드
    public JwtTokenModel generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + 86400000);
        // 인증된 사용자의 권한 정보와 만료 시간을 담고 있음
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성 : Access Token의 갱신을 위해 사용 됨
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 86400000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtTokenModel.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
     * 주어진 Access token을 복호화하여 사용자의 인증 정보(Authentication)를 생성
     * 토큰의 Claims에서 권한 정보를 추출하고, User 객체를 생성하여 Authentication 객체로 반환
     * Collection<? extends GrantedAuthority>로 리턴받는 이유
     * 권한 정보를 다양한 타입의 객체로 처리할 수 있고, 더 큰 유연성과 확장성을 가질 수 있음
     */
    public Authentication getAuthentication(String accessToken) {
        // Jwt 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        // 토큰의 클레임에서 권한 정보를 가져옴. "auth" 클레임은 토큰에 저장된 권한 정보를 나타냄
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new) // 가져온 권한 정보를 SimpleGrantedAuthority 객체로 변환하여 컬렉션에 추가
                .collect(Collectors.toList());

        /**
         * UserDetails 객체를 생성하여 주체(subject)와 권한 정보, 기타 필요한 정보를 설정
         * UserDetails 객체를 만들어서 Authentication return
         * UserDetails: interface, User: UserDetails를 구현한 class
         */
        //주어진 토큰의 클레임에서 "sub" 클레임의 값을 반환 토큰의 주체를 나타냄. ex) 사용자의 식별자나 이메일 주소
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        // UsernamepasswordAuthenticationToken 객체를 생성하여 주체와 권한 정보를 포함한 인증(Authentication) 객체를 생성
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드, 주어진 토큰을 검증하여 유효성을 확인
    public boolean validateToken(String token) {
        try {
            // Jwts.parserBuilder를 사용하여 토큰의 서명 키를 설정하고, 예외 처리를 통해 토큰의 유효성 여부를 판단
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token); // parseClaimsJws() 메서드가 JWT 토큰의 검증과 파싱을 모두 수행
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            // 토큰이 올바른 형식이 아니거나 클레임이 비어있는 경우 등에 발생
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }


    /**
     * parseClaims()
     * 클레임(Claims): 토큰에서 사용할 정보의 조각
     * 주어진 Access token을 복호화하고, 만료된 토큰인 경우에도 Claims 반환
     */
    // accessToken
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken) // parseClaimsJws() 메서드가 JWT 토큰의 검증과 파싱을 모두 수행
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }

    }
}