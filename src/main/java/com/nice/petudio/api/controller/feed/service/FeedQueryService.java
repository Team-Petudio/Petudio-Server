package com.nice.petudio.api.controller.feed.service;

import com.nice.petudio.api.controller.feed.dto.PostInquiryResponse;
import com.nice.petudio.api.controller.feed.dto.PostsInquiryResponse;
import com.nice.petudio.domain.concept.repository.ConceptRepository;
import com.nice.petudio.domain.feed.Feed;
import com.nice.petudio.domain.feed.repository.FeedRepository;
import com.nice.petudio.domain.feedlike.repository.LikeRepository;
import com.nice.petudio.domain.pet.repository.PetRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedQueryService {
    private final FeedRepository feedRepository;
    private final LikeRepository likeRepository;
    private final PetRepository petRepository;
    private final ConceptRepository conceptRepository;

    private static final int INQUIRY_POST_COUNT = 50;
    private static final int RECOMMENDED_POST_COUNT = 20;

    // 좋아요 순으로 INQUIRY_POST_COUNT 개의 포스트 조회 후, 셔플 후 RECOMMENDED_POST_COUNT 개의 포스트 return
    // 포스트가 20개 이하면 셔플 후 바로 return
    public PostsInquiryResponse inquiryRecommendedPosts(Optional<Long> memberId) {
        HashMap<Long, Long> feedIdToLikeCount = likeRepository.findLikeCountTopN(INQUIRY_POST_COUNT);

        List<Feed> likeCountTopNFeeds = feedRepository.findFeedsByIds(feedIdToLikeCount.keySet());
        // 셔플 후 개수 조정
        Collections.shuffle(likeCountTopNFeeds);
        if (likeCountTopNFeeds.size() > RECOMMENDED_POST_COUNT) {
            likeCountTopNFeeds = likeCountTopNFeeds.subList(0, RECOMMENDED_POST_COUNT);
        }

        Set<Long> petIds = likeCountTopNFeeds.stream()
                .map(Feed::getPetId)
                .collect(Collectors.toSet());
        Map<Long, String> petIdToNames = petRepository.findPetIdToNameByPetIds(petIds);

        Set<Long> conceptIds = likeCountTopNFeeds.stream()
                .map(Feed::getPetId)
                .collect(Collectors.toSet());

        for (Long conceptId : conceptIds) {
            System.out.println("conceptId = " + conceptId);
        }

        System.out.println("conceptIds Size = " + conceptIds.size());
        Map<Long, String> conceptIdToNames = conceptRepository.findConceptIdToNameByConceptIds(conceptIds);

        List<PostInquiryResponse> postInfos = getPostsInfos(memberId, likeCountTopNFeeds, feedIdToLikeCount,
                petIdToNames, conceptIdToNames);
        return PostsInquiryResponse.from(postInfos);
    }

    private List<PostInquiryResponse> getPostsInfos(Optional<Long> memberId, List<Feed> likeCountTopNFeeds,
                                                    HashMap<Long, Long> feedIdToLikeCount,
                                                    Map<Long, String> petIdToNames,
                                                    Map<Long, String> conceptIdToNames) {
        if (memberId.isPresent()) {
            return inquiryRecommendedPostsWithIsLiked(memberId, likeCountTopNFeeds, feedIdToLikeCount, petIdToNames,
                    conceptIdToNames);
        }
        return inquiryRecommendedPostWithoutIsLiked(likeCountTopNFeeds, feedIdToLikeCount, petIdToNames,
                conceptIdToNames);
    }

    private List<PostInquiryResponse> inquiryRecommendedPostsWithIsLiked(Optional<Long> memberId,
                                                                         List<Feed> likeCountTopNFeeds,
                                                                         HashMap<Long, Long> feedIdToLikeCount,
                                                                         Map<Long, String> petIdToNames,
                                                                         Map<Long, String> conceptIdToNames) {
        List<PostInquiryResponse> postInfos = new ArrayList<>();
        List<Long> feedIds = likeCountTopNFeeds.stream()
                .map(Feed::getId)
                .toList();

        List<Long> memberLikedFeedIds = likeRepository.findMemberLikedFeedIds(memberId.get(), feedIds);

        for (Feed feed : likeCountTopNFeeds) {
            long likeCount = feedIdToLikeCount.get(feed.getId());
            boolean isLiked = memberLikedFeedIds.contains(feed.getId());
            String petName = petIdToNames.get(feed.getPetId());
            String conceptName = conceptIdToNames.get(feed.getConceptId());

            postInfos.add(PostInquiryResponse.fromEntity(feed, likeCount, isLiked, petName, conceptName));
        }
        return postInfos;
    }

    private List<PostInquiryResponse> inquiryRecommendedPostWithoutIsLiked(List<Feed> likeCountTopNFeeds,
                                                                           HashMap<Long, Long> feedIdToLikeCount,
                                                                           Map<Long, String> petIdToNames,
                                                                           Map<Long, String> conceptIdToNames) {
        List<PostInquiryResponse> postInfos = new ArrayList<>();

        for (Feed feed : likeCountTopNFeeds) {
            Long likeCount = feedIdToLikeCount.get(feed.getId());
            String petName = petIdToNames.get(feed.getPetId());
            String conceptName = conceptIdToNames.get(feed.getConceptId());

            postInfos.add(PostInquiryResponse.fromEntity(feed, likeCount, petName, conceptName));
        }
        return postInfos;
    }
}
