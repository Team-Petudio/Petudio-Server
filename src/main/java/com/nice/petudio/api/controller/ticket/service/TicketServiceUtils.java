package com.nice.petudio.api.controller.ticket.service;

import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.NotFoundException;
import com.nice.petudio.domain.member.point.Ticket;
import com.nice.petudio.domain.member.point.repository.TicketRepository;

public class TicketServiceUtils {

    public static Ticket findTicketByMemberId(TicketRepository ticketRepository, final Long memberId) {
        return ticketRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_TICKET_EXCEPTION,
                        String.format("회원ID(%d) 의 티켓 정보가 존재하지 않습니다.", memberId)));
    }
}
