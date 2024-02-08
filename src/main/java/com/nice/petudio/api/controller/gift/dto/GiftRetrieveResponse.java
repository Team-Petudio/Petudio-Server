package com.nice.petudio.api.controller.gift.dto;

import com.nice.petudio.domain.gift.Gift;
import java.time.LocalDateTime;

public record GiftRetrieveResponse(String giftCode, boolean isUsed, LocalDateTime expiredAt, boolean isExpired) {

    public static GiftRetrieveResponse fromEntity(final Gift gift, boolean isExpired) {
        return new GiftRetrieveResponse(gift.getCode(), gift.getIsUsed(), gift.getExpiredAt(), isExpired);
    }
}
