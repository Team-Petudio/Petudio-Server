package com.nice.petudio.domain.concept;

import com.nice.petudio.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "concepts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Concept extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concept_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "concept_type", length = 30, nullable = false)
    private ConceptType conceptType;

    @Column(name = "concept_main_image_uri", length = 200, nullable = false)
    private String mainImageUri;

    @Column(name = "concept_success_image1_uri", length = 200, nullable = false)
    private String successImage1Uri;

    @Column(name = "concept_success_image2_uri", length = 200, nullable = false)
    private String successImage2Uri;

    @Column(name = "concept_success_image3_uri", length = 200, nullable = false)
    private String successImage3Uri;

    @Column(name = "concept_success_image4_uri", length = 200, nullable = false)
    private String successImage4Uri;

    @Column(name = "concept_fail_image1_uri", length = 200, nullable = false)
    private String failImage1lUri;

    @Column(name = "concept_fail_image2_uri", length = 200, nullable = false)
    private String failImage2Uri;

    @Column(name = "concept_fail_image3_uri", length = 200, nullable = false)
    private String failImage3Uri;

    @Column(name = "concept_fail_image4_uri", length = 200, nullable = false)
    private String failImage4Uri;


    public boolean validateIsNew(LocalDateTime now) {
        // 컨셉이 생긴지 90일이 지나지 않았으면 'New 컨셉'에 해당
        return getCreatedAt().isAfter(now.minusDays(90));
    }
}
