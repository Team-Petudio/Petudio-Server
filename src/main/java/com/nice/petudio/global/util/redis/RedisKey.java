package com.nice.petudio.global.util.redis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RedisKey {
    REFRESH_TOKEN("RT:");

    private final String key;
}
