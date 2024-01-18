package com.nice.petudio.api.controller.member.service;

import com.nice.petudio.api.controller.member.dto.CreateMemberRequest;
import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long registerMember(CreateMemberRequest request) {
        MemberServiceUtils.validateNotExistsMember(memberRepository, request.getSocialId(), request.getSocialType());
        Member member = memberRepository.save(
                Member.newInstance(request.getSocialId(), request.getSocialType()));
        return member.getId();
    }
}
