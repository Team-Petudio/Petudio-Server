package com.nice.petudio.domain.pet.repository;

import static com.nice.petudio.domain.pet.QPet.pet;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PetRepositoryImpl implements PetRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> findIdsByMemberId(Long memberId) {
        return queryFactory
                .select(pet.id)
                .where(pet.member_id.eq(memberId))
                .fetch();
    }
}
