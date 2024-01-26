package com.nice.petudio.domain.concept.repository;

import com.nice.petudio.domain.concept.Concept;
import java.util.List;

public interface ConceptRepositoryCustom {
    List<Concept> findAllConcept();
}
