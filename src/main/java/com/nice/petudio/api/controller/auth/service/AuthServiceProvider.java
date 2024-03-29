package com.nice.petudio.api.controller.auth.service;

import com.nice.petudio.api.controller.auth.service.impl.GoogleAuthService;
import com.nice.petudio.api.controller.auth.service.impl.KakaoAuthService;
import com.nice.petudio.domain.member.SocialType;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthServiceProvider {

	private static final Map<SocialType, AuthService> authServiceMap = new HashMap<>();

	private final KakaoAuthService kakaoAuthService;
	private final GoogleAuthService googleAuthService;

	@PostConstruct
	void initializeAuthServicesMap() {
		authServiceMap.put(SocialType.KAKAO, kakaoAuthService);
		authServiceMap.put(SocialType.GOOGLE, googleAuthService);
	}

	public AuthService getAuthService(SocialType socialType) {
		return authServiceMap.get(socialType);
	}
}
