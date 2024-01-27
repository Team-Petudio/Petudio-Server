package com.nice.petudio.api.controller.pet;

import com.nice.petudio.api.controller.pet.service.PetCommandService;
import com.nice.petudio.api.dto.ApiResponse;
import com.nice.petudio.common.auth.auth.Auth;
import com.nice.petudio.common.auth.resolver.MemberId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PetController {
    private final PetCommandService petCommandService;

    @Auth
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "[인증] 반려동물 정보 삭제")
    @DeleteMapping("/pet/delete/{petId}")
    public ApiResponse<?> deletePetInfo(@MemberId Long memberId, @PathVariable final Long petId) {
        petCommandService.deletePetInfo(petId, memberId);
        return ApiResponse.success();
    }
}
