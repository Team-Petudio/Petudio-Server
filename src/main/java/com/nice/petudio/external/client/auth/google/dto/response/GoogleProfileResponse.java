package com.nice.petudio.external.client.auth.google.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nice.petudio.api.controller.member.dto.CreateMemberRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GoogleProfileResponse{

    private String id;

    public CreateMemberRequest toSignUpRequest(final CreateMemberRequest request) {
        return CreateMemberRequest.of(id, request.getSocialType(), request.getFcmToken());
    }
}
