package com.nice.petudio.api.controller.gift.service;

import com.nice.petudio.api.controller.gift.dto.GiftGenerateResponse;
import com.nice.petudio.api.controller.ticket.service.TicketServiceUtils;
import com.nice.petudio.domain.gift.Gift;
import com.nice.petudio.domain.gift.repository.GiftRepository;
import com.nice.petudio.domain.member.point.Ticket;
import com.nice.petudio.domain.member.point.repository.TicketRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class GiftCommandService {
    private final GiftRepository giftRepository;
    private final TicketRepository ticketRepository;
    private final GiftCodeGenerator giftCodeGenerator;

    public GiftGenerateResponse generateGift(final Long memberId) {
        String giftCode = giftCodeGenerator.generate();
        Gift gift = Gift.newInstance(memberId, giftCode);

        giftRepository.save(gift);
        return GiftGenerateResponse.from(giftCode);
    }

    public void useGift(final Long memberId, final String giftCode) {
        Gift gift = GiftServiceUtils.findByGiftCode(giftRepository, giftCode);
        gift.use(memberId, LocalDateTime.now());

        Ticket ticket = TicketServiceUtils.findTicketByMemberId(ticketRepository, memberId);
        ticket.addAmount(1);
        log.info(String.format("[기프트 사용] 기프트 (GIFT_ID: %d)가 회원 (MEMBER_ID: %d)에 의해 사용되었습니다.", gift.getId(), memberId));
    }
}
