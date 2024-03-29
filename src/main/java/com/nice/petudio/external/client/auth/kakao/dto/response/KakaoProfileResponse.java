package com.nice.petudio.external.client.auth.kakao.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoProfileResponse {

	private String id;
	private KakaoAccount kakaoAccount;


	public String getEmail() {
		return kakaoAccount.getEmail();
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@JsonIgnoreProperties(ignoreUnknown = true)
	class KakaoAccount {
		private String email;
	}
}
