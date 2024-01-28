package com.nice.petudio.api.controller.pet.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nice.petudio.domain.pet.FurColor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record PetAddRequest(
        @NotBlank
        String name,
        @NotBlank
        FurColor furColor,
        @Size(min = 10, max = 12, message = "petPhotoUris List size must be between 10 and 12")
        List<String> petPhotoUris) {
}
