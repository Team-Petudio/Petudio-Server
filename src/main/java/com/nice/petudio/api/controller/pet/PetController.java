package com.nice.petudio.api.controller.pet;

import com.nice.petudio.api.controller.pet.dto.CreatePetImagesUploadUrlsResponse;
import com.nice.petudio.api.controller.pet.dto.DeletePetImagesRequest;
import com.nice.petudio.api.controller.pet.dto.FindMyPetsResponse;
import com.nice.petudio.api.controller.pet.dto.PetAddRequest;
import com.nice.petudio.api.controller.pet.dto.CreatePetImagesUploadUrlsRequest;
import com.nice.petudio.api.controller.pet.service.PetCommandService;
import com.nice.petudio.api.controller.pet.service.PetQueryService;
import com.nice.petudio.api.dto.ApiResponse;
import com.nice.petudio.common.auth.auth.Auth;
import com.nice.petudio.common.auth.resolver.MemberId;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PetController {
    private final PetCommandService petCommandService;
    private final PetQueryService petQueryService;

    @Auth
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "[인증] 반려동물 정보 삭제")
    @DeleteMapping("/pet/delete/{petId}")
    public ApiResponse<?> deletePetInfo(@MemberId final Long memberId, @PathVariable final Long petId) {
        petCommandService.deletePetInfo(petId, memberId);
        return ApiResponse.success();
    }

    @Auth
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "[인증] 내 반려동물 정보 조회")
    @GetMapping("/pets")
    public ApiResponse<FindMyPetsResponse> getMyPetsInfo(@MemberId final Long memberId) {
        return ApiResponse.success(petQueryService.findPetsInfoByMemberId(memberId));
    }

    @Auth
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "[인증] 반려동물 정보 저장")
    @PostMapping("/pet/add")
    public ApiResponse<?> addPetInfo(@MemberId final Long memberId, @RequestBody final PetAddRequest request) {
        petCommandService.addPetInfo(memberId, request);
        return ApiResponse.success();
    }

    @Auth
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "[인증] 반려동물 이미지 저장용 S3 PreSignedURL 요청")
    @GetMapping("/pet/images/presigned-url")
    public ApiResponse<CreatePetImagesUploadUrlsResponse> createPreSignedUrlForSavePetImages(
            @MemberId final Long memberId, @RequestBody @Valid final CreatePetImagesUploadUrlsRequest request) {
        return ApiResponse.success(petQueryService.createPreSignedUrlForSavePetImages(memberId, request));
    }

    @Auth
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "[인증] 반려동물 이미지 삭제 요청")
    @GetMapping("/pet/images/delete")
    public ApiResponse<?> deletePetImagesOnS3(@RequestBody @Valid final DeletePetImagesRequest request) {
        petQueryService.deletePetImagesOnS3(request);
        return ApiResponse.success();
    }
}
