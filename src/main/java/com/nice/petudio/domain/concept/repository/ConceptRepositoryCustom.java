package com.nice.petudio.domain.concept.repository;

import com.nice.petudio.domain.concept.Concept;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ConceptRepositoryCustom {
    List<Concept> findAllConcept();

    Map<Long, String> findConceptIdToNameByConceptIds(Set<Long> conceptIds);
}
