package com.nice.petudio.api.controller.feed;

import com.nice.petudio.api.controller.feed.dto.PostConceptPhotoRequest;
import com.nice.petudio.api.controller.feed.service.FeedCommandService;
import com.nice.petudio.api.controller.feed.service.FeedQueryService;
import com.nice.petudio.api.dto.ApiResponse;
import com.nice.petudio.common.auth.auth.Auth;
import com.nice.petudio.common.auth.resolver.MemberId;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
