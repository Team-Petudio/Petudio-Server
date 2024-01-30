package com.nice.petudio.domain.pet.repository;

import com.nice.petudio.domain.pet.Pet;
import java.util.List;
import java.util.Optional;

public interface PetRepositoryCustom {

    List<Long> findIdsByMemberId(Long memberId);

    List<Pet> findPetsByMemberId(Long memberId);

    Optional<Pet> findPetByS3DirectoryPath(String s3DirectoryPath);
}
