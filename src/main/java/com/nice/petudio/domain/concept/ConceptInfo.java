package com.nice.petudio.domain.concept;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ConceptInfo {
    DOG_3D("3D 애니메이션 컨셉", "concept.dog.3d.", PetType.DOG),
    DOG_TRENDY("트랜디 룩북 컨셉", "concept.dog.trendy.", PetType.DOG);

    private final String conceptName;
    private final String messagePrefix;
    private final PetType petType;
}
