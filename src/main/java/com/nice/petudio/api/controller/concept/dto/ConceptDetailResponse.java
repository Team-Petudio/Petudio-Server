package com.nice.petudio.api.controller.concept.dto;


import com.nice.petudio.domain.concept.Concept;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ConceptDetailResponse(List<String> successImageUris, List<String> failImageUris) {

    public static ConceptDetailResponse fromEntity(Concept concept) {
        return ConceptDetailResponse.builder()
                .successImageUris(List.of(concept.getSuccessImage1Uri(), concept.getSuccessImage2Uri(),
                        concept.getSuccessImage3Uri(), concept.getSuccessImage4Uri()))
                .failImageUris(List.of(concept.getFailImage1lUri(), concept.getFailImage2Uri(),
                        concept.getFailImage3Uri(), concept.getFailImage4Uri()))
                .build();
    }
}
