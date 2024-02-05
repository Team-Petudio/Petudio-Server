package com.nice.petudio.domain.member.point.repository;

import com.nice.petudio.domain.member.point.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends TicketRepositoryCustom, JpaRepository<Ticket, Long> {
}
