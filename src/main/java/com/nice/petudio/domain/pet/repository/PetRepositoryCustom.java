package com.nice.petudio.domain.pet.repository;

import com.nice.petudio.domain.pet.Pet;
import java.util.List;

public interface PetRepositoryCustom {

    List<Long> findIdsByMemberId(Long memberId);

    List<Pet> findPetsByMemberId(Long memberId);
}
