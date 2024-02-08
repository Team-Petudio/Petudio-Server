package com.nice.petudio.domain.concept.repository;

import static com.nice.petudio.domain.concept.QConcept.concept;

import com.nice.petudio.domain.concept.Concept;
import com.nice.petudio.domain.concept.ConceptInfo;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConceptRepositoryImpl implements ConceptRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Concept> findAllConcept() {
        return queryFactory
                .selectFrom(concept)
                .fetch();
    }

    @Override
    public Map<Long, String> findConceptIdToNameByConceptIds(Set<Long> conceptIds) {
        List<Tuple> conceptIdToInfos = queryFactory
                .select(concept.id, concept.info)
                .from(concept)
                .where(concept.id.in(conceptIds))
                .fetch();

        System.out.println("conceptIdToInfos Size = " + conceptIdToInfos.size());
        Map<Long, String> result = new HashMap<>();
        for (Tuple conceptIdToInfo : conceptIdToInfos) {
            long conceptId = conceptIdToInfo.get(0, Long.class);
            String conceptName = conceptIdToInfo.get(1, ConceptInfo.class).getConceptName();
            result.put(conceptId, conceptName);

            System.out.println(String.format("conceptId(%d), conceptName(%s)", conceptId, conceptName));
        }
        System.out.println("result Size = " + result.size());

        return result;
    }
}
