package com.nice.petudio.api.controller.pet.dto;

import jakarta.validation.constraints.NotBlank;

public record DeletePetImagesRequest(
        @NotBlank
        String s3DirectoryPath) {
}
