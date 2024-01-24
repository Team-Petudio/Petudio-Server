package com.nice.petudio.api.controller.auth.service.impl;

import com.nice.petudio.api.controller.auth.dto.request.LoginRequest;
import com.nice.petudio.api.controller.auth.service.AuthService;
import com.nice.petudio.api.controller.member.dto.CreateMemberRequest;
import com.nice.petudio.api.controller.member.service.MemberCommandService;
import com.nice.petudio.api.controller.member.service.MemberServiceUtils;
import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.SocialType;
import com.nice.petudio.domain.member.repository.MemberRepository;
import com.nice.petudio.external.client.auth.google.GoogleApiCaller;
import com.nice.petudio.external.client.auth.google.dto.response.GoogleProfileResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class GoogleAuthService implements AuthService {

    private final GoogleApiCaller googleApiCaller;

    private final MemberRepository memberRepository;

    private final MemberCommandService memberCommandService;

    @Override
    public Long signUp(LoginRequest request) {
        GoogleProfileResponse profileInfo = googleApiCaller.getProfileInfo(request.getToken());
        CreateMemberRequest createMemberRequest = CreateMemberRequest.of(profileInfo.getId(), profileInfo.getEmail(),
                SocialType.GOOGLE,
                request.getFcmToken());
        Long memberId = memberCommandService.registerMember(createMemberRequest);

        writeSignUpLog(memberId, SocialType.GOOGLE);
        return memberId;
    }

    @Override
    public Long login(LoginRequest request) {
        GoogleProfileResponse response = googleApiCaller.getProfileInfo(request.getToken());
        Optional<Member> member = MemberServiceUtils.findOptionalMemberBySocialInfo(memberRepository, response.getId(),
                SocialType.GOOGLE);

        return loginOrSignUp(request, member);
    }

    @Override
    public void writeSignUpLog(Long memberId, SocialType socialType) {
        log.info(String.format(SIGN_UP_LOG_MESSAGE, memberId, socialType));
    }
}
