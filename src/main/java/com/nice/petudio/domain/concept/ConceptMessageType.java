package com.nice.petudio.domain.concept;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ConceptMessageType {
    NAME("name"),
    DESCRIPTION("description"),
    TARGET("target");

    private final String type;
}
