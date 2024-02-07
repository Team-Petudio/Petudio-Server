package com.nice.petudio.api.controller.feed.dto;

import com.nice.petudio.domain.feed.Feed;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record PostInquiryResponse(Long feedId, Long memberId, Long petId, Long conceptId, String profileImageUri, long likeCount) {

    public static PostInquiryResponse fromEntity(final Feed feed, final long likeCount) {
        return PostInquiryResponse.builder()
                .feedId(feed.getId())
                .memberId(feed.getMemberId())
                .petId(feed.getPetId())
                .conceptId(feed.getConceptId())
                .profileImageUri(feed.getProfileImageUri())
                .likeCount(likeCount)
                .build();
    }
}
