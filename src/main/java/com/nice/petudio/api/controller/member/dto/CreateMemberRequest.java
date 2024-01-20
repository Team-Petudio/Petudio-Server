package com.nice.petudio.api.controller.member.dto;

import com.nice.petudio.domain.member.SocialType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class CreateMemberRequest {

    private String socialId;
    private SocialType socialType;
    private String fcmToken;

    public static CreateMemberRequest of(String socialId, SocialType socialType, String fcmToken) {
        return CreateMemberRequest.builder()
                .socialId(socialId)
                .socialType(socialType)
                .fcmToken(fcmToken)
                .build();
    }
}
