package com.nice.petudio.api.controller.pet.service;

import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.NotFoundException;
import com.nice.petudio.domain.pet.Pet;
import com.nice.petudio.domain.pet.repository.PetRepository;
import java.util.List;

public class PetServiceUtils {

    public static List<Long> findPetsByMemberId(PetRepository petRepository, Long memberId) {
        return petRepository.findIdsByMemberId(memberId);
    }

    public static Pet findPetById(PetRepository petRepository, Long petId) {
        return petRepository.findById(petId)
                .orElseThrow(
                        () -> new NotFoundException(ErrorCode.NOT_FOUND_PET_INFO_EXCEPTION,
                                String.format("존재하지 않는 petId(%d) 입니다.", petId)));
    }
}
