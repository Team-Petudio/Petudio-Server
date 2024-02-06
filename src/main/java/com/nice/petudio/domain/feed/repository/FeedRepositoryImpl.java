package com.nice.petudio.domain.feed.repository;

import static com.nice.petudio.domain.feed.QFeed.feed;

import com.nice.petudio.domain.feed.Feed;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Feed> findFeedByMemberAndPetAndConceptId(Long memberId, Long petId, Long conceptId) {
        return queryFactory.selectFrom(feed)
                .where(feed.memberId.eq(memberId))
                .where(feed.petId.eq(petId))
                .where(feed.conceptId.eq(conceptId))
                .fetch();
    }
}
