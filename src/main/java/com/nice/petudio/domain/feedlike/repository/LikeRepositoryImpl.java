package com.nice.petudio.domain.feedlike.repository;

import static com.nice.petudio.domain.feed.QFeed.feed;
import static com.nice.petudio.domain.feedlike.QLike.like;
import static com.nice.petudio.domain.member.QMember.member;

import com.nice.petudio.domain.feedlike.Like;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Like> findByMemberAndFeedId(Long memberId, Long feedId) {
        return Optional.ofNullable(queryFactory.selectFrom(like)
                .where(like.memberId.eq(memberId))
                .where(like.feedId.eq(feedId))
                .fetchOne());
    }

    @Override
    public HashMap<Long, Long> findLikeCountTopN(int n) {
        List<Tuple> likeCountTopN = queryFactory.select(feed.id, like.feedId.count())
                .from(feed)
                .leftJoin(like).on(feed.id.eq(like.feedId))
                .groupBy(feed.id)
                .orderBy(like.feedId.count().desc())
                .limit(n)
                .fetch();

        HashMap<Long, Long> feedIdToLikeCount = new HashMap<>();
        for (Tuple feedIdAndLikeCount : likeCountTopN) {
            Long feedId = feedIdAndLikeCount.get(0, Long.class);
            Long likeCount = feedIdAndLikeCount.get(1, Long.class);
            feedIdToLikeCount.put(feedId, likeCount);
        }

        return feedIdToLikeCount;
    }

    @Override
    public List<Long> findMemberLikedFeedIds(Long memberId, List<Long> feedIds) {
        return queryFactory.select(like.feedId)
                .from(like)
                .where(like.feedId.in(feedIds))
                .where(like.memberId.eq(memberId))
                .where(like.isValid.eq(true))
                .fetch();
    }
}
