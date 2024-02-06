package com.nice.petudio.api.controller.gift.service;

import com.nice.petudio.api.controller.gift.dto.GiftRetrieveResponse;
import com.nice.petudio.api.controller.gift.dto.GiftsRetrieveResponse;
import com.nice.petudio.domain.gift.Gift;
import com.nice.petudio.domain.gift.repository.GiftRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GiftQueryService {
    private final GiftRepository giftRepository;

    public GiftsRetrieveResponse findGiftsByMemberId(final Long memberId) {
        List<Gift> gifts = giftRepository.findUsableGiftsByBuyerId(memberId);

        List<GiftRetrieveResponse> giftsInfo = new ArrayList<>();
        for (Gift gift : gifts) {
            giftsInfo.add(GiftRetrieveResponse.of(gift.getCode(), gift.getExpiredAt()));
        }

        return new GiftsRetrieveResponse(giftsInfo);
    }
}
