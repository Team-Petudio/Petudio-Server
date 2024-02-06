package com.nice.petudio.api.controller.gift.dto;

import java.time.LocalDateTime;

public record GiftRetrieveResponse(String giftCode, LocalDateTime expiredAt) {

    public static GiftRetrieveResponse of(String giftCode, LocalDateTime expiredAt) {
        return new GiftRetrieveResponse(giftCode, expiredAt);
    }
}
