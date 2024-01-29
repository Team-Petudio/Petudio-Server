package com.nice.petudio.api.controller.pet.dto;

import java.util.List;

public record CreatePetImagesUploadUrlsResponse(List<String> preSignedUrls) {
}
