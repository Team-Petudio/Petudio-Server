package com.nice.petudio.api.controller.auth.service.impl;

import com.nice.petudio.api.controller.auth.dto.request.LoginRequest;
import com.nice.petudio.api.controller.auth.dto.request.SignUpRequest;
import com.nice.petudio.api.controller.auth.service.AuthService;
import com.nice.petudio.api.controller.member.dto.CreateMemberRequest;
import com.nice.petudio.api.controller.member.service.MemberCommandService;
import com.nice.petudio.api.controller.member.service.MemberServiceUtils;
import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.SocialType;
import com.nice.petudio.domain.member.repository.MemberRepository;
import com.nice.petudio.external.client.auth.google.GoogleApiCaller;
import com.nice.petudio.external.client.auth.google.dto.response.GoogleProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class GoogleAuthService implements AuthService {

	private final GoogleApiCaller googleApiCaller;

	private final MemberRepository memberRepository;

	private final MemberCommandService memberCommandService;

	@Override
	public Long signUp(SignUpRequest request) {
		GoogleProfileResponse profileInfo = googleApiCaller.getProfileInfo(request.getToken());
		CreateMemberRequest createMemberRequest = CreateMemberRequest.of(profileInfo.getId(), SocialType.GOOGLE,
				request.getFcmToken());

		return memberCommandService.registerMember(createMemberRequest);
	}

	@Override
	public Long login(LoginRequest request) {
		GoogleProfileResponse response = googleApiCaller.getProfileInfo(request.getToken());
		Member member = MemberServiceUtils.findMemberBySocialIdAndSocialType(memberRepository, response.getId(),
			SocialType.GOOGLE);
		member.updateFcmToken(request.getFcmToken());

		return member.getId();
	}
}
