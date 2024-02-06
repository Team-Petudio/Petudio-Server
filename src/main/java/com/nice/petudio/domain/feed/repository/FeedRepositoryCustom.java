package com.nice.petudio.domain.feed.repository;

import com.nice.petudio.domain.feed.Feed;
import java.util.List;

public interface FeedRepositoryCustom {
    List<Feed> findFeedByMemberAndPetAndConceptId(final Long memberId, final Long petId, final Long conceptId);
}
