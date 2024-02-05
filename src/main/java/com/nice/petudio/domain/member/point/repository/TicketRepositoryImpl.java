package com.nice.petudio.domain.member.point.repository;


import static com.nice.petudio.domain.member.point.QTicket.ticket;

import com.nice.petudio.domain.member.point.Ticket;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Ticket> findByMemberId(Long memberId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(ticket)
                .where(ticket.memberId.eq(memberId))
                .fetchOne());
    }
}
