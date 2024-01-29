package com.nice.petudio.api.controller.pet.dto;

import com.nice.petudio.domain.pet.FurColor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record PetAddRequest(
        @NotBlank
        String name,
        @NotBlank
        FurColor furColor,
        @NotBlank
        String s3DirectoryPath,
        @Size(min = 10, max = 12, message = "petPhotoUris의 사이즈는 10개 이상 12개 이하여야 합니다.")
        List<String> petPhotoUris) {
}
