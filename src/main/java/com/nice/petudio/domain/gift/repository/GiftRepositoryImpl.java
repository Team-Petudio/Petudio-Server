package com.nice.petudio.domain.gift.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GiftRepositoryImpl implements GiftRepositoryCustom {
    private final JPAQueryFactory queryFactory;
}
