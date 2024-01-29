package com.nice.petudio.domain.pet;

import com.nice.petudio.api.controller.pet.dto.PetAddRequest;
import com.nice.petudio.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pets")
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "pet_name", length = 30, nullable = false)
    private String name;

    @Column(name = "pet_fur_color", length = 30, nullable = false)
    private FurColor furColor;

    @Column(name = "pet_image_s3_directory_path", length = 100, nullable = false)
    private String s3DirectoryPath;

    @Column(name = "pet_photos", nullable = false)
    private String petPhotos; // JSON type


    public static Pet newInstance(PetAddRequest request, String petPhotoUrisJson, Long memberId) {
        return Pet.builder()
                .memberId(memberId)
                .name(request.name())
                .furColor(request.furColor())
                .petPhotos(petPhotoUrisJson)
                .s3DirectoryPath(request.s3DirectoryPath())
                .build();
    }
}
