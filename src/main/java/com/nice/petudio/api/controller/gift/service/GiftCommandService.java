package com.nice.petudio.api.controller.gift.service;

import com.nice.petudio.api.controller.gift.dto.GiftGenerateResponse;
import com.nice.petudio.domain.gift.Gift;
import com.nice.petudio.domain.gift.repository.GiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GiftCommandService {
    private final GiftRepository giftRepository;
    private final GiftCodeGenerator giftCodeGenerator;

    public GiftGenerateResponse generateGift() {
        String giftCode = giftCodeGenerator.generate();
        Gift gift = Gift.newInstance(giftCode);

        giftRepository.save(gift);
        return GiftGenerateResponse.from(giftCode);
    }
}
