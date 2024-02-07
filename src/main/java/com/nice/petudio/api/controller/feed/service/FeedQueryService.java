package com.nice.petudio.api.controller.feed.service;

import com.nice.petudio.api.controller.feed.dto.PostInquiryResponse;
import com.nice.petudio.api.controller.feed.dto.PostsInquiryResponse;
import com.nice.petudio.domain.feed.Feed;
import com.nice.petudio.domain.feed.repository.FeedRepository;
import com.nice.petudio.domain.feedlike.repository.LikeRepository;
import com.querydsl.core.Tuple;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedQueryService {
    private final FeedRepository feedRepository;
    private final LikeRepository likeRepository;

    private static final int RECOMMENDED_POST_COUNT = 100;
    private static final int RANDOM_PICK_POST_COUNT = 20;

    // 좋아요 순으로 100개의 포스트 조회 후, 셔플 후 0~19 index return
    // 포스트가 20개 이하면 셔플 후 바로 return
    public PostsInquiryResponse inquiryRecommendedPosts() {
        List<PostInquiryResponse> postInfos = new ArrayList<>();

        HashMap<Long, Long> feedIdToLikeCount = likeRepository.findLikeCountTopN(RECOMMENDED_POST_COUNT);
        List<Feed> feedsLikeCountTopN = feedRepository.findFeedsByIds(feedIdToLikeCount.keySet());

        for (Feed feed : feedsLikeCountTopN) {
            Long likeCount = feedIdToLikeCount.get(feed.getId());
            postInfos.add(PostInquiryResponse.fromEntity(feed, likeCount));
        }
        return PostsInquiryResponse.pickRandomNPosts(postInfos, RANDOM_PICK_POST_COUNT);
    }
}
