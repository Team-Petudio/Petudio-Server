package com.nice.petudio.api.controller.member.dto;

import com.nice.petudio.domain.member.SocialType;
import lombok.Builder;

@Builder
public record MemberMyPageResponse(SocialType socialType, String email, boolean notificationStatus, int pointAmount) {
    public static MemberMyPageResponse of(SocialType socialType, String email, boolean notificationStatus, int pointAmount) {
        return MemberMyPageResponse
                .builder()
                .socialType(socialType)
                .email(email)
                .notificationStatus(notificationStatus)
                .pointAmount(pointAmount)
                .build();
    }
}