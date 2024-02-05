package com.nice.petudio.api.controller.member.service;

import com.nice.petudio.domain.member.point.Ticket;
import com.nice.petudio.domain.member.point.repository.TicketRepository;
import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.NotFoundException;

public class PointServiceUtils {

    public static Ticket findPointByMemberId(TicketRepository ticketRepository, final Long memberId) {
        return ticketRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorCode.NOT_FOUND_MEMBER_INFO_EXCEPTION,
                        String.format("memberId(%d)의 Point 정보를 찾을 수 없습니다.", memberId)));
    }
}
