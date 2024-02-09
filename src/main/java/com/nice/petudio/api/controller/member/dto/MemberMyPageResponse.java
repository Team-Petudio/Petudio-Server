package com.nice.petudio.api.controller.member.dto;

import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.SocialType;
import lombok.Builder;

@Builder
public record MemberMyPageResponse(Long memberId, SocialType socialType, String email, boolean notificationStatus,
                                   int ticketAmount) {
    public static MemberMyPageResponse of(final Member member, boolean notificationStatus, int ticketAmount) {
        return MemberMyPageResponse
                .builder()
                .memberId(member.getId())
                .socialType(member.getSocialType())
                .email(member.getEmail())
                .notificationStatus(notificationStatus)
                .ticketAmount(ticketAmount)
                .build();
    }
}
