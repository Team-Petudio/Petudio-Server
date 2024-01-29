package com.nice.petudio.api.controller.pet.dto;

import java.util.List;

public record CreatePetImagesUploadUrlsResponse(
        String s3DirectoryPath,
        List<PetImageUploadInfo> petImageUploadInfos) {
}
