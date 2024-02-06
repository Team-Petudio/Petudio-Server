package com.nice.petudio.api.controller.gift.service;

import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.NotFoundException;
import com.nice.petudio.domain.gift.Gift;
import com.nice.petudio.domain.gift.repository.GiftRepository;

public class GiftServiceUtils {

    public static Gift findByGiftId(GiftRepository giftRepository, final String giftCode) {
        return giftRepository.findByGiftCode(giftCode)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_GIFT_EXCEPTION,
                        String.format("해당하는 GiftCode (%s)를 가진 Gift가 존재하지 않습니다.", giftCode)));
    }
}
