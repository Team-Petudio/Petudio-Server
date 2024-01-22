package com.nice.petudio.common.config.redis.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RedisKey {
    REFRESH_TOKEN("RT:");

    private final String key;
}
