package com.nice.petudio.api.controller.concept.dto;


import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ConceptRetrieveResponse(String mainImageUri, String name, String description, Boolean isNew) {
}
