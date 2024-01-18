package com.nice.petudio.global.util.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtKey {
    MEMBER_ID("memberId");

    private final String key;
}
