package com.nice.petudio.domain.feedlike.repository;

import com.nice.petudio.domain.feedlike.Like;
import java.util.HashMap;
import java.util.Optional;

public interface LikeRepositoryCustom {
    Optional<Like> findByMemberAndFeedId(Long memberId, Long feedId);

    HashMap<Long, Long> findLikeCountTopN(int n);
}
