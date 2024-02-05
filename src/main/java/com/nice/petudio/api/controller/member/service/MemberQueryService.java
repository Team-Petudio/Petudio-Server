package com.nice.petudio.api.controller.member.service;

import com.nice.petudio.api.controller.member.dto.MemberMyPageResponse;
import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.point.Ticket;
import com.nice.petudio.domain.member.point.repository.TicketRepository;
import com.nice.petudio.domain.member.repository.MemberRepository;
import com.nice.petudio.domain.member.setting.Setting;
import com.nice.petudio.domain.member.setting.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService {
    private final MemberRepository memberRepository;
    private final TicketRepository ticketRepository;
    private final SettingRepository settingRepository;

    public MemberMyPageResponse getMemberMypageInfo(final Long memberId) {
        Member member = MemberServiceUtils.findMemberById(memberRepository, memberId);
        Ticket ticket = PointServiceUtils.findPointByMemberId(ticketRepository, memberId);
        Setting setting = SettingServiceUtils.findSettingByMemberId(settingRepository, memberId);

        return MemberMyPageResponse.of(member.getSocialType(), member.getEmail(), setting.getNotificationStatus(),
                ticket.getAmount());
    }
}
