package com.nice.petudio.external.client.auth.kakao;


import com.nice.petudio.external.client.auth.kakao.dto.response.KakaoProfileResponse;

public interface KakaoApiCaller {

	KakaoProfileResponse getProfileInfo(String accessToken);

}
