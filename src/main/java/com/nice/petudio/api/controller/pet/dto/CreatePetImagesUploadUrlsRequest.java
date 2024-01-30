package com.nice.petudio.api.controller.pet.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record CreatePetImagesUploadUrlsRequest(
        @Min(value = 10, message = "이미지 갯수는 10개 이상, 12개 이하이어야 합니다.")
        @Max(value = 12, message = "이미지 갯수는 10개 이상, 12개 이하이어야 합니다.")
        short imageAmount
) {}
