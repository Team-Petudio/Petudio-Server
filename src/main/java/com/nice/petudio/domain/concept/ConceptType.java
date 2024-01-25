package com.nice.petudio.domain.concept;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ConceptType {
    CONCEPT_3D("concept.3d."),
    CONCEPT_TRENDY("concept.trendy.");

    private final String messagePrefix;
}
