package com.nice.petudio.domain.gift.repository;

import com.nice.petudio.domain.gift.Gift;
import java.util.Optional;

public interface GiftRepositoryCustom {
    Optional<Gift> findByGiftCode(String giftCode);
}
