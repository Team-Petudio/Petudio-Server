package com.nice.petudio.domain.member.repository;

import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.SocialType;
import java.util.Optional;

public interface MemberRepositoryCustom {
    boolean existsBySocialIdAndSocialType(String socialId, SocialType socialType);

    Optional<Member> findMemberById(Long id);

    Optional<Member> findMemberBySocialIdAndSocialType(String socialId, SocialType socialType);
}
