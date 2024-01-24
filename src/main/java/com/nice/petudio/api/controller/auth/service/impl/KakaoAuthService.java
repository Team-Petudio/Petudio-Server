package com.nice.petudio.api.controller.auth.service.impl;

import com.nice.petudio.api.controller.auth.dto.request.LoginRequest;
import com.nice.petudio.api.controller.auth.service.AuthService;
import com.nice.petudio.api.controller.member.dto.CreateMemberRequest;
import com.nice.petudio.api.controller.member.service.MemberCommandService;
import com.nice.petudio.api.controller.member.service.MemberServiceUtils;
import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.SocialType;
import com.nice.petudio.domain.member.repository.MemberRepository;
import com.nice.petudio.external.client.auth.kakao.KakaoApiCaller;
import com.nice.petudio.external.client.auth.kakao.dto.response.KakaoProfileResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class KakaoAuthService implements AuthService {

    private final KakaoApiCaller kakaoApiCaller;

    private final MemberRepository memberRepository;

    private final MemberCommandService memberCommandService;

    @Override
    public Long signUp(LoginRequest request) {
        KakaoProfileResponse profileInfo = kakaoApiCaller.getProfileInfo(request.getToken());
        CreateMemberRequest createMemberRequest = CreateMemberRequest.of(profileInfo.getId(), profileInfo.getEmail(),
                SocialType.KAKAO,
                request.getFcmToken());
        Long memberId = memberCommandService.registerMember(createMemberRequest);

        writeSignUpLog(memberId, SocialType.KAKAO);
        return memberId;
    }

    @Override
    public Long login(LoginRequest request) {
        KakaoProfileResponse response = kakaoApiCaller.getProfileInfo(request.getToken());
        Optional<Member> member = MemberServiceUtils.findOptionalMemberBySocialInfo(
                memberRepository, response.getId(),
                SocialType.KAKAO);

        return loginOrSignUp(request, member);
    }

    @Override
    public void writeSignUpLog(Long memberId, SocialType socialType) {
        log.info(String.format(SIGN_UP_LOG_MESSAGE, memberId, socialType));
    }
}
