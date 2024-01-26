package com.nice.petudio.api.controller.concept;

import com.nice.petudio.api.controller.concept.dto.ConceptsResponse;
import com.nice.petudio.api.controller.concept.service.ConceptQueryService;
import com.nice.petudio.api.dto.ApiResponse;
import com.nice.petudio.common.auth.auth.Auth;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ConceptController {
    private final ConceptQueryService conceptQueryService;

    @Auth
    @Operation(summary = "[인증] AI 프로필 컨셉 전체 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/concepts")
    public ApiResponse<ConceptsResponse> getAllConceptsInfo() {
        return ApiResponse.success(conceptQueryService.findAllConcepts());
    }
}
