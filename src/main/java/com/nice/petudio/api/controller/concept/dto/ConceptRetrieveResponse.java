package com.nice.petudio.api.controller.concept.dto;


import com.nice.petudio.domain.concept.PetType;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ConceptRetrieveResponse(Long conceptId, String mainImageUri, String name, String descriptionMessage,
                                      PetType petType, Boolean isNew) {
}
