package com.nice.petudio.api.controller.auth.service;

import com.nice.petudio.api.controller.member.service.MemberServiceUtils;
import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.repository.MemberRepository;
import com.nice.petudio.global.auth.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CommonAuthService {

	private final MemberRepository memberRepository;

	private final JwtTokenService jwtTokenService;

	public void logout(Long memberId) {
		Member member = MemberServiceUtils.findMemberById(memberRepository, memberId);
		jwtTokenService.expireRefreshToken(member.getId());
	}
}
