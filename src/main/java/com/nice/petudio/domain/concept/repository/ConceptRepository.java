package com.nice.petudio.domain.concept.repository;

import com.nice.petudio.domain.concept.Concept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConceptRepository extends ConceptRepositoryCustom, JpaRepository<Concept, Long> {
}
