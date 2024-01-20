package com.nice.petudio.api.controller.auth.dto.request;

import com.nice.petudio.api.controller.member.dto.CreateMemberRequest;
import com.nice.petudio.domain.member.SocialType;
import com.nice.petudio.external.client.auth.kakao.dto.response.KakaoProfileResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpRequest {

	@Schema(description = "소셜 로그인 타입", example = "KAKAO")
	@NotNull(message = "{auth.socialType.notNull}")
	private SocialType socialType;

	@Schema(description = "소셜 토큰", example = "eyJhbGciOiJIUzUxdfadfadsMiJ9.udnKnDSK08EuX56E5k-")
	@NotBlank(message = "{auth.token.notBlank}")
	private String token;

	@Schema(description = "fcm 토큰", example = "dfdafjdslkfjslfjslifsjvmdsklvdosijsmvsdjvosadjvosd")
	@NotBlank(message = "{auth.fcmToken.notBlank}")
	private String fcmToken;


	public CreateMemberRequest toCreateMemberDto(KakaoProfileResponse response) {
		return CreateMemberRequest.of(response.getId(), socialType, fcmToken, response.getNickname(), response.getThumbnailImage());
	}
}