package com.nice.petudio.api.controller.pet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nice.petudio.api.controller.pet.dto.FindMyPetResponse;
import com.nice.petudio.api.controller.pet.dto.FindMyPetsResponse;
import com.nice.petudio.api.controller.pet.dto.PetPhotos;
import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.InternalServerException;
import com.nice.petudio.domain.pet.Pet;
import com.nice.petudio.domain.pet.repository.PetRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PetQueryService {
    private final PetRepository petRepository;
    private final ObjectMapper objectMapper;

    public FindMyPetsResponse findPetsInfoByMemberId(final Long memberId) {
        List<FindMyPetResponse> petsInfo = new ArrayList<>();

        List<Pet> pets = petRepository.findPetsByMemberId(memberId);
        for (Pet pet : pets) {
            try {
                PetPhotos petPhotos = objectMapper.readValue(pet.getPetPhotos(), PetPhotos.class);
                petsInfo.add(new FindMyPetResponse(pet.getId(), pet.getName(), petPhotos.petImage1Uri()));
            } catch (JsonProcessingException e) {
                throw new InternalServerException(ErrorCode.INTERNAL_SERVER_EXCEPTION,
                        "애완동물 사진 데이터를 객체와 매핑하는 과정에서 오류가 발생하였습니다.");
            }
        }
        return new FindMyPetsResponse(petsInfo);
    }
}
