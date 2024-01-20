package com.nice.petudio.domain.member.point.repository;

import static com.nice.petudio.domain.member.point.QPoint.point;

import com.nice.petudio.domain.member.point.Point;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Point> findByMemberId(Long memberId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(point)
                .where(point.memberId.eq(memberId))
                .fetchOne());
    }
}
