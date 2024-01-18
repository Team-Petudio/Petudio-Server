package com.nice.petudio.domain.member.repository;

import static com.nice.petudio.domain.member.QMember.member;

import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.SocialType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsBySocialIdAndSocialType(String socialId, SocialType socialType) {
        return Optional.ofNullable(queryFactory.selectOne()
                .from(member)
                .where(
                        member.socialInfo.socialId.eq(socialId),
                        member.socialInfo.socialType.eq(socialType)
                )
                .fetchFirst()).isPresent();
    }

    @Override
    public Optional<Member> findMemberById(Long id) {
        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .where(member.id.eq(id))
                .fetchOne());
    }

    @Override
    public Optional<Member> findMemberBySocialIdAndSocialType(String socialId, SocialType socialType) {
        return Optional.ofNullable(queryFactory
                .selectFrom(member)
                .where(
                        member.socialInfo.socialId.eq(socialId),
                        member.socialInfo.socialType.eq(socialType)
                )
                .fetchOne());
    }
}
