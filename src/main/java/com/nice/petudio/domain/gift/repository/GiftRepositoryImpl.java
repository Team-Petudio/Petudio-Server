package com.nice.petudio.domain.gift.repository;

import static com.nice.petudio.domain.gift.QGift.gift;

import com.nice.petudio.domain.gift.Gift;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GiftRepositoryImpl implements GiftRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Gift> findByGiftCode(String giftCode) {
        return Optional.ofNullable(queryFactory
                .selectFrom(gift)
                .where(gift.code.eq(giftCode))
                .fetchOne());
    }

    @Override
    public List<Gift> findUsableGiftsByBuyerId(Long memberId) {
        return queryFactory.selectFrom(gift)
                .where(gift.buyerId.eq(memberId))
                .where(gift.isUsed.eq(false))
                .where(gift.expiredAt.gt(LocalDateTime.now()))
                .fetch();
    }
}
