package com.nice.petudio.domain.concept.repository;

import com.nice.petudio.domain.concept.Concept;
import com.nice.petudio.domain.concept.ConceptType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConceptRepository extends ConceptRepositoryCustom, JpaRepository<Concept, Long> {
    Optional<Concept> findByConceptType(ConceptType conceptType);
}
