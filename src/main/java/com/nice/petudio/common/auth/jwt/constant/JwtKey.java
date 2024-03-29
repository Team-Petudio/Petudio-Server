package com.nice.petudio.common.auth.jwt.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtKey {
    MEMBER_ID("memberId");

    private final String key;
}
