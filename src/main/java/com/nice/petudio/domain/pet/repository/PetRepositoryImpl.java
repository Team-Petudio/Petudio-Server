package com.nice.petudio.domain.pet.repository;

import static com.nice.petudio.domain.pet.QPet.pet;

import com.nice.petudio.domain.pet.Pet;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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

    @Override
    public Map<Long, String> findPetIdToNameByPetIds(Set<Long> petIds) {
        List<Tuple> petIdToNames = queryFactory.
                select(pet.id, pet.name)
                .from(pet)
                .where(pet.id.in(petIds))
                .fetch();

        Map<Long, String> result = new HashMap<>();
        for (Tuple petIdToName : petIdToNames) {
            result.put(petIdToName.get(pet.id), petIdToName.get(pet.name));
        }
        return result;
    }
}
