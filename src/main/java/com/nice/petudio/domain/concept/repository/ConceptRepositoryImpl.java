package com.nice.petudio.domain.concept.repository;

import static com.nice.petudio.domain.concept.QConcept.concept;

import com.nice.petudio.domain.concept.Concept;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConceptRepositoryImpl implements ConceptRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Concept> findAllConcept() {
        return queryFactory
                .selectFrom(concept)
                .fetch();
    }
}
