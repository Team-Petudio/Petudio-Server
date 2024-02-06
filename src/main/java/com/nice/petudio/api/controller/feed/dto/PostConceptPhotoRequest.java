package com.nice.petudio.api.controller.feed.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostConceptPhotoRequest(
        @NotNull
        Long petId,
        @NotNull
        Long conceptId,
        @NotNull
        Long albumId,
        @NotBlank
        String profileImageUri) {
}
