package com.nice.petudio.api.controller.gift.service;

import com.nice.petudio.api.controller.gift.dto.GiftRetrieveResponse;
import com.nice.petudio.api.controller.gift.dto.GiftsRetrieveResponse;
import com.nice.petudio.domain.gift.Gift;
import com.nice.petudio.domain.gift.repository.GiftRepository;
import java.time.LocalDateTime;
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
        List<Gift> gifts = giftRepository.findGiftsByBuyerId(memberId);

        List<GiftRetrieveResponse> giftsInfo = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (Gift gift : gifts) {
            giftsInfo.add(GiftRetrieveResponse.fromEntity(gift, gift.getExpiredAt().isAfter(now)));
        }

        return new GiftsRetrieveResponse(giftsInfo);
    }
}
