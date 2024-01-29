package com.nice.petudio.domain.concept;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ConceptInfo {
    DOG_3D("concept.dog.3d.", PetType.DOG),
    DOG_TRENDY("concept.dog.trendy.", PetType.DOG);

    private final String messagePrefix;
    private final PetType petType;
}
