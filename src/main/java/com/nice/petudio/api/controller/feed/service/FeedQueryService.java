package com.nice.petudio.api.controller.feed.service;

import com.nice.petudio.api.controller.feed.dto.PostInquiryResponse;
import com.nice.petudio.api.controller.feed.dto.PostsInquiryResponse;
import com.nice.petudio.domain.feed.Feed;
import com.nice.petudio.domain.feed.repository.FeedRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedQueryService {
    private final FeedRepository feedRepository;

    public PostsInquiryResponse inquiryPosts() {
        List<PostInquiryResponse> postInfos = new ArrayList<>();

        for (Feed feed : feedRepository.findAll()) {
            postInfos.add(PostInquiryResponse.fromEntity(feed));
        }
        return new PostsInquiryResponse(postInfos);
    }
}
