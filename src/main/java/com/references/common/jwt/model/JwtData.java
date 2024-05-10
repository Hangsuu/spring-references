package com.references.common.jwt.model;

import lombok.Getter;

@Getter
public enum JwtData {
    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken"),
    BEARER("Bearer");

    private final String name;

    JwtData(String name) {
        this.name = name;
    }
}
