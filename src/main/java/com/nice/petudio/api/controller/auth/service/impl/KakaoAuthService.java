package com.nice.petudio.api.controller.auth.service.impl;

import com.nice.petudio.api.controller.auth.dto.request.LoginRequest;
import com.nice.petudio.api.controller.auth.dto.request.SignUpRequest;
import com.nice.petudio.api.controller.auth.service.AuthService;
import com.nice.petudio.api.controller.member.service.MemberCommandService;
import com.nice.petudio.api.controller.member.service.MemberServiceUtils;
import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.SocialType;
import com.nice.petudio.domain.member.repository.MemberRepository;
import com.nice.petudio.external.client.auth.kakao.KakaoApiCaller;
import com.nice.petudio.external.client.auth.kakao.dto.response.KakaoProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class KakaoAuthService implements AuthService {

	private final KakaoApiCaller kakaoApiCaller;

	private final MemberRepository memberRepository;

	private final MemberCommandService memberCommandService;

	@Override
	public Long signUp(SignUpRequest request) {
		KakaoProfileResponse response = kakaoApiCaller.getProfileInfo(request.getToken());
		return memberCommandService.registerMember(request.toCreateMemberDto(response));
	}

	@Override
	public Long login(LoginRequest request) {
		KakaoProfileResponse response = kakaoApiCaller.getProfileInfo(request.getToken());
		Member member = MemberServiceUtils.findMemberBySocialIdAndSocialType(memberRepository, response.getId(),
			SocialType.KAKAO);
		member.updateFcmToken(request.getFcmToken());
		return member.getId();
	}
}
