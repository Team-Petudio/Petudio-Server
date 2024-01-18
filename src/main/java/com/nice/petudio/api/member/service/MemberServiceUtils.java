package com.nice.petudio.api.member.service;

import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.repository.MemberRepository;
import com.nice.petudio.global.exception.NotFoundException;
import com.nice.petudio.global.exception.error.ErrorCode;

public class MemberServiceUtils {

    public static Member findMemberById(MemberRepository memberRepository, Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_MEMBER_EXCEPTION,
                        String.format("====> 존재하지 않는 memberId(%d) 입니다.", memberId)));
    }
}
