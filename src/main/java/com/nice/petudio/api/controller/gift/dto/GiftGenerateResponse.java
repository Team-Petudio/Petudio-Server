package com.nice.petudio.api.controller.gift.dto;

public record GiftGenerateResponse(String giftCode) {
    public static GiftGenerateResponse from(String giftCode) {
        return new GiftGenerateResponse(giftCode);
    }
}
