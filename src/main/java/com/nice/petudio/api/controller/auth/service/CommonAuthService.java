package com.nice.petudio.api.controller.auth.service;

import com.nice.petudio.api.controller.member.service.MemberServiceUtils;
import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.repository.MemberRepository;
import com.nice.petudio.common.auth.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CommonAuthService {

	private final MemberRepository memberRepository;

	private final JwtUtils jwtUtils;

	public void logout(final Long memberId) {
		Member member = MemberServiceUtils.findMemberById(memberRepository, memberId);
		jwtUtils.expireRefreshToken(member.getId());
	}
}
