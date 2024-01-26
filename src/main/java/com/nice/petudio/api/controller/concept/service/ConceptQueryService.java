package com.nice.petudio.api.controller.concept.service;

import com.nice.petudio.api.controller.concept.dto.ConceptDetailResponse;
import com.nice.petudio.api.controller.concept.dto.ConceptsRetrieveResponse;
import com.nice.petudio.domain.concept.Concept;
import com.nice.petudio.domain.concept.repository.ConceptRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConceptQueryService {
    private final MessageSource messageSource;
    private final ConceptRepository conceptRepository;

    public ConceptsRetrieveResponse findAllConceptsInfo() {
        return ConceptsRetrieveResponse.convertEntitiesToDto(conceptRepository.findAllConcept(), messageSource,
                LocalDateTime.now());
    }

    public ConceptDetailResponse findConceptDetailById(Long conceptId) {
        Concept concept = ConceptServiceUtils.findByConceptId(conceptRepository, conceptId);
        return ConceptDetailResponse.fromEntity(concept);
    }
}
