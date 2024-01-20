package com.nice.petudio.api.controller.pet.service;

import com.nice.petudio.domain.pet.repository.PetRepository;
import java.util.List;

public class PetServiceUtils {

    public static List<Long> findPetsByMemberId(PetRepository petRepository, Long memberId) {
        return petRepository.findIdsByMemberId(memberId);
    }
}
