package com.nice.petudio.api.controller.feed.dto;

import com.nice.petudio.domain.feed.Feed;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record PostInquiryResponse(Long feedId, Long memberId, String petName, String conceptName,
                                  String profileImageUri, long likeCount, boolean isLiked) {

    public static PostInquiryResponse fromEntity(final Feed feed, long likeCount, String petName, String conceptName) {
        return PostInquiryResponse.builder()
                .feedId(feed.getId())
                .memberId(feed.getMemberId())
                .petName(petName)
                .conceptName(conceptName)
                .profileImageUri(feed.getProfileImageUri())
                .likeCount(likeCount)
                .isLiked(false)
                .build();
    }

    public static PostInquiryResponse fromEntity(final Feed feed, long likeCount, boolean isLiked, String petName,
                                                 String conceptName) {
        return PostInquiryResponse.builder()
                .feedId(feed.getId())
                .memberId(feed.getMemberId())
                .petName(petName)
                .conceptName(conceptName)
                .profileImageUri(feed.getProfileImageUri())
                .likeCount(likeCount)
                .isLiked(isLiked)
                .build();
    }
}
