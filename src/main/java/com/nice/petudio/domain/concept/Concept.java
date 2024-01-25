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

    @Column(name = "concept_main_image", length = 200, nullable = false)
    private String mainImage;

    @Column(name = "concept_sub_image1", length = 200, nullable = false)
    private String subImage1;

    @Column(name = "concept_sub_image2", length = 200, nullable = false)
    private String subImage2;

    @Column(name = "concept_sub_image3", length = 200, nullable = false)
    private String subImage3;

    @Column(name = "concept_sub_image4", length = 200, nullable = false)
    private String subImage4;

    @Column(name = "concept_fail_image1", length = 200, nullable = false)
    private String failImage1;

    @Column(name = "concept_fail_image2", length = 200, nullable = false)
    private String failImage2;

    @Column(name = "concept_fail_image3", length = 200, nullable = false)
    private String failImage3;

    @Column(name = "concept_fail_image4", length = 200, nullable = false)
    private String failImage4;
}
