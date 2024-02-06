package com.nice.petudio.api.controller.gift.service;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GiftCodeGenerator {

    public String generate() {
        String giftCode = UUID.randomUUID().toString();
        giftCode = giftCode.toUpperCase();
        giftCode = giftCode.replace("-", "");

        log.info(String.format("[GIFT CODE 생성] %s", giftCode));
        return giftCode;
    }
}
