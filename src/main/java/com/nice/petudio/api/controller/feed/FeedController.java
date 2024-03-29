package com.nice.petudio.api.controller.feed;

import com.nice.petudio.api.controller.feed.dto.PostConceptPhotoRequest;
import com.nice.petudio.api.controller.feed.dto.PostsInquiryResponse;
import com.nice.petudio.api.controller.feed.service.FeedCommandService;
import com.nice.petudio.api.controller.feed.service.FeedQueryService;
import com.nice.petudio.api.dto.ApiResponse;
import com.nice.petudio.common.auth.auth.Auth;
import com.nice.petudio.common.auth.resolver.MemberId;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FeedController {
    private final FeedCommandService feedCommandService;
    private final FeedQueryService feedQueryService;

    @Operation(summary = "[인증] 피드에 컨셉 포토 게시")
    @Auth
    @PostMapping("/feed/post")
    public ApiResponse<?> postMyConceptPhoto(@MemberId final Long memberId,
                                             @Valid @RequestBody final PostConceptPhotoRequest request) {
        feedCommandService.postConceptPhoto(memberId, request);
        return ApiResponse.success();
    }

    // TODO: 커서 기반 무한스크롤 방식으로 개선
    @Operation(summary = "피드 조회")
    @GetMapping("/feeds")
    public ApiResponse<PostsInquiryResponse> getRecommendedPost(
            @RequestParam(required = false) final Optional<Long> memberId) {
        return ApiResponse.success(feedQueryService.inquiryRecommendedPosts(memberId));
    }

    @Operation(summary = "[인증] 피드의 포스트 좋아요")
    @Auth
    @PatchMapping("/feed/like/{feedId}")
    public ApiResponse<?> likePost(@MemberId final Long memberId, @PathVariable final Long feedId) {
        feedCommandService.likePost(memberId, feedId);
        return ApiResponse.success();
    }

    @Operation(summary = "[인증] 피드 포스트 삭제")
    @Auth
    @DeleteMapping("/feed/delete/{feedId}")
    public ApiResponse<?> deletePost(@MemberId final Long memberId, @PathVariable final Long feedId) {
        feedCommandService.deletePost(memberId, feedId);
        return ApiResponse.success();
    }
}
