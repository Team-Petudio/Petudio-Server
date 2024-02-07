package com.nice.petudio.domain.feed.repository;

import com.nice.petudio.domain.feed.Feed;
import java.util.List;
import java.util.Set;

public interface FeedRepositoryCustom {
    List<Feed> findFeedByMemberAndPetAndConceptId(final Long memberId, final Long petId, final Long conceptId);
    List<Feed> findFeedsByIds(Set<Long> ids);
}
