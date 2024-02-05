package com.nice.petudio.domain.member.point.repository;

import com.nice.petudio.domain.member.point.Ticket;
import java.util.Optional;

public interface TicketRepositoryCustom {
    Optional<Ticket> findByMemberId(Long memberId);
}
