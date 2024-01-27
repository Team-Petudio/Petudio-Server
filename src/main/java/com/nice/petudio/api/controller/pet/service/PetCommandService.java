package com.nice.petudio.api.controller.pet.service;

import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.NotFoundException;
import com.nice.petudio.domain.pet.Pet;
import com.nice.petudio.domain.pet.repository.PetRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PetCommandService {
    private final PetRepository petRepository;

    public void deletePetInfo(final Long petId, final Long memberId) {
        Pet pet = PetServiceUtils.findPetById(petRepository, petId);

        if(pet.getMemberId().equals(memberId)) {
            petRepository.delete(pet);
            log.info(String.format("애완동물 삭제 완료 [petId = %d]", petId));
        }
        throw new NotFoundException(ErrorCode.NOT_FOUND_PET_INFO_EXCEPTION,
                String.format("해당 memberId(%d) 에 애완동물 목록에 petId(%d) 에 해당하는 데이터가 존재하지 않습니다.", memberId, petId));
    }
}
