package com.nice.petudio.api.controller.album.dto;

import com.nice.petudio.domain.album.Album;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record AlbumRetrieveResponse(Long albumId, String petName, String conceptName, String thumbnailUri, String profileImageUris, LocalDateTime createdAt) {
    public static AlbumRetrieveResponse of(final Album album, String petName, String conceptName) {
        return AlbumRetrieveResponse.builder()
                .albumId(album.getId())
                .petName(petName)
                .conceptName(conceptName)
                .thumbnailUri(album.getThumbnailImageUri())
                .profileImageUris(album.getProfileImageUris())
                .createdAt(album.getCreatedAt())
                .build();

    }
}
