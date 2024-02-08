package com.nice.petudio.domain.pet.repository;

import com.nice.petudio.domain.pet.Pet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface PetRepositoryCustom {

    List<Long> findIdsByMemberId(Long memberId);

    List<Pet> findPetsByMemberId(Long memberId);

    Optional<Pet> findPetByS3DirectoryPath(String s3DirectoryPath);

    Map<Long, String> findPetIdToNameByPetIds(Set<Long> petIds);
}
