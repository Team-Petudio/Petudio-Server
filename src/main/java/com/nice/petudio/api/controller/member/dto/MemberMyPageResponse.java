package com.nice.petudio.api.controller.member.dto;

import com.nice.petudio.domain.member.SocialType;
import lombok.Builder;

@Builder
public record MemberMyPageResponse(SocialType socialType, String email, boolean notificationStatus, int ticketAmount) {
    public static MemberMyPageResponse of(SocialType socialType, String email, boolean notificationStatus, int ticketAmount) {
        return MemberMyPageResponse
                .builder()
                .socialType(socialType)
                .email(email)
                .notificationStatus(notificationStatus)
                .ticketAmount(ticketAmount)
                .build();
    }
}
