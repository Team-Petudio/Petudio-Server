package com.nice.petudio.api.controller.concept.service;

import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.NotFoundException;
import com.nice.petudio.domain.concept.Concept;
import com.nice.petudio.domain.concept.repository.ConceptRepository;

public class ConceptServiceUtils {

    public static Concept findByConceptId(ConceptRepository conceptRepository, Long conceptId) {
        return conceptRepository.findById(conceptId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_CONCEPT_EXCEPTION,
                        String.format("존재하지 않는 conceptId(%d) 입니다.", conceptId)));
    }
}
