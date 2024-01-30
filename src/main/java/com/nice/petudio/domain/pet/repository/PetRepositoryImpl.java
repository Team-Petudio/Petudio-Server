package com.nice.petudio.domain.pet.repository;

import static com.nice.petudio.domain.pet.QPet.pet;

import com.nice.petudio.domain.pet.Pet;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PetRepositoryImpl implements PetRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> findIdsByMemberId(Long memberId) {
        return queryFactory
                .select(pet.id)
                .from(pet)
                .where(pet.memberId.eq(memberId))
                .fetch();
    }

    @Override
    public List<Pet> findPetsByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(pet)
                .where(pet.memberId.eq(memberId))
                .fetch();
    }

    @Override
    public Optional<Pet> findPetByS3DirectoryPath(String s3DirectoryPath) {
        return Optional.ofNullable(queryFactory
                .selectFrom(pet)
                .where(pet.s3DirectoryPath.eq(s3DirectoryPath))
                .fetchOne());
    }
}
