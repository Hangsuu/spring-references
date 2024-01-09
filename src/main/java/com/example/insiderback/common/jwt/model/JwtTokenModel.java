package com.example.insiderback.common.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtTokenModel {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
