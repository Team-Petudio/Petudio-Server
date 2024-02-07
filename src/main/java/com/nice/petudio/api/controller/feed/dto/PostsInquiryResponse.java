package com.nice.petudio.api.controller.feed.dto;

import java.util.Collections;
import java.util.List;

public record PostsInquiryResponse(List<PostInquiryResponse> postInfos) {
    public static PostsInquiryResponse from(List<PostInquiryResponse> postInfos) {

        return new PostsInquiryResponse(postInfos);
    }
}
