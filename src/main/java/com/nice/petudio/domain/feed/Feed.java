package com.nice.petudio.domain.feed;

import com.nice.petudio.api.controller.feed.dto.PostConceptPhotoRequest;
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
@Table(name = "feeds")
@Builder(access = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "pet_id", nullable = false)
    private Long petId;

    @Column(name = "concept_id", nullable = false)
    private Long conceptId;

    @Column(name = "profile_image_uri", length = 300, nullable = false)
    private String profileImageUri;

    public static Feed newInstance(Long memberId, PostConceptPhotoRequest request) {
        return Feed.builder()
                .memberId(memberId)
                .petId(request.petId())
                .conceptId(request.conceptId())
                .profileImageUri(request.profileImageUri())
                .build();
    }
}
