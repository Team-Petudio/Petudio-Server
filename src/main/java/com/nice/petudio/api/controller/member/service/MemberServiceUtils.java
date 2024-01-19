package com.nice.petudio.api.controller.member.service;

import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.SocialType;
import com.nice.petudio.domain.member.repository.MemberRepository;
import com.nice.petudio.global.exception.model.ConflictException;
import com.nice.petudio.global.exception.model.NotFoundException;
import com.nice.petudio.global.exception.error.ErrorCode;
import java.util.Optional;

public class MemberServiceUtils {

    public static Member findMemberById(MemberRepository memberRepository, Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_MEMBER_EXCEPTION,
                        String.format("존재하지 않는 memberId(%d) 입니다.", memberId)));
    }

    static void validateNotExistsMember(MemberRepository memberRepository, String socialId,
                                        SocialType socialType) {
        if (memberRepository.existsBySocialIdAndSocialType(socialId, socialType)) {
            throw new ConflictException(ErrorCode.CONFLICT_MEMBER_EXCEPTION, String.format("이미 존재하는 회원 소셜정보 (%s - %s) 입니다", socialId, socialType));
        }
    }

    public static Member findMemberBySocialIdAndSocialType(MemberRepository memberRepository, String socialId,
                                                           SocialType socialType) {
        Optional<Member> member = memberRepository.findMemberBySocialIdAndSocialType(socialId, socialType);
        return member.orElseThrow(() -> new NotFoundException(
                ErrorCode.NOT_FOUND_MEMBER_EXCEPTION,
                String.format("존재하지 않는 회원 소셜정보 (%s - %s) 입니다", socialType, socialId)));
    }
}
