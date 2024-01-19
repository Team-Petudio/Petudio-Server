package com.nice.petudio.api.controller.member;

import com.nice.petudio.api.controller.member.dto.ChangeNotificationStatusResponse;
import com.nice.petudio.api.controller.member.service.MemberCommandService;
import com.nice.petudio.api.dto.ApiResponse;
import com.nice.petudio.global.auth.auth.Auth;
import com.nice.petudio.global.auth.resolver.MemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {
    private final MemberCommandService memberCommandService;

    @Auth
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/member/notification/status-change")
    public ApiResponse<ChangeNotificationStatusResponse> changeMemberNotificationStatus(@MemberId final Long memberId,
                                                                                        @RequestParam("status") final boolean notificationStatus) {
        return ApiResponse.success(memberCommandService.changeMemberNotificationStatus(memberId, notificationStatus));
    }
}
