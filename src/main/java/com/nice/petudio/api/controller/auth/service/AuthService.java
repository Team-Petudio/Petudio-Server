package com.nice.petudio.api.controller.auth.service;


import com.nice.petudio.api.controller.auth.dto.request.LoginRequest;
import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.SocialType;
import java.util.Optional;

public interface AuthService {
	String SIGN_UP_LOG_MESSAGE = "회원가입 성공 - [memberId(%d) / SocialType(%s)]";

	Long signUp(LoginRequest request);

	Long login(LoginRequest request);

	void writeSignUpLog(Long memberId, SocialType socialType);

	default Long loginOrSignUp(LoginRequest request, Optional<Member> member) {
		if(member.isEmpty()) {
			return signUp(request);
		}

		Member loggedInMember = member.get();
		loggedInMember.updateFcmToken(request.getFcmToken());
		return loggedInMember.getId();
	}
}
