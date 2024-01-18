package com.nice.petudio.global.config.redis.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RedisKey {
    REFRESH_TOKEN("RT:");

    private final String key;
}
