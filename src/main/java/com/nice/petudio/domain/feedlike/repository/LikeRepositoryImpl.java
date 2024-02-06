package com.nice.petudio.domain.feedlike.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepositoryCustom {
    private final JPAQueryFactory queryFactory;
}
