package com.nice.petudio.api.controller.pet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nice.petudio.api.controller.pet.dto.PetAddRequest;
import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.ConflictException;
import com.nice.petudio.common.exception.model.InternalServerException;
import com.nice.petudio.common.exception.model.NotFoundException;
import com.nice.petudio.domain.pet.Pet;
import com.nice.petudio.domain.pet.repository.PetRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PetCommandService {
    private final ObjectMapper objectMapper;
    private final PetRepository petRepository;

    public void deletePetInfo(final Long petId, final Long memberId) {
        Pet pet = PetServiceUtils.findPetById(petRepository, petId);

        if (pet.getMemberId().equals(memberId)) {
            petRepository.delete(pet);
            log.info(String.format("애완동물 삭제 완료 [petId = %d]", petId));
        }
        throw new NotFoundException(ErrorCode.NOT_FOUND_PET_INFO_EXCEPTION,
                String.format("해당 memberId(%d) 에 애완동물 목록에 petId(%d) 에 해당하는 데이터가 존재하지 않습니다.", memberId, petId));
    }

    public void addPetInfo(final Long memberId, final PetAddRequest request) {
        validatePetS3PathConflict(request);

        String petPhotoUrisJson = petPhotoUrisToJson(request.petPhotoUris());
        Pet pet = Pet.newInstance(request, petPhotoUrisJson, memberId);

        petRepository.save(pet);
    }

    private void validatePetS3PathConflict(PetAddRequest request) {
        Optional<Pet> petByS3DirectoryPath = petRepository.findPetByS3DirectoryPath(request.s3DirectoryPath());
        if(petByS3DirectoryPath.isPresent()) {
            throw new ConflictException(ErrorCode.CONFLICT_PET_S3_PATH_EXCEPTION,
                    "이미 해당 S3경로(%s) 에 반려동물의 이미지가 존재합니다.");
        }
    }

    private String petPhotoUrisToJson(List<String> petPhotoUris) {
        try {
            Map<String, String> petImageUris = new HashMap<>();
            for (int i = 0; i < petPhotoUris.size(); i++) {
                petImageUris.put("petImage" + (i + 1) + "Uri", petPhotoUris.get(i));
            }

            return objectMapper.writeValueAsString(petImageUris);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER_EXCEPTION,
                    "애완동물 사진 데이터를 JSON으로 매핑하는 과정에서 오류가 발생하였습니다.");
        }
    }
}
