package com.nice.petudio.api.controller.auth.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class TokenVO {

	private String accessToken;
	private String refreshToken;

	public static TokenVO of(String accessToken, String refreshToken) {
		return TokenVO.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}
