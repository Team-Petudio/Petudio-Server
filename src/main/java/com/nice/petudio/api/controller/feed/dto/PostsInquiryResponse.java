package com.nice.petudio.api.controller.feed.dto;

import java.util.Collections;
import java.util.List;

public record PostsInquiryResponse(List<PostInquiryResponse> postInfos) {
    public static PostsInquiryResponse pickRandomNPosts(List<PostInquiryResponse> postInfos, int n) {
        Collections.shuffle(postInfos);
        if (postInfos.size() < n) {
            return new PostsInquiryResponse(postInfos);
        }

        return new PostsInquiryResponse(postInfos.subList(0, n));
    }
}
