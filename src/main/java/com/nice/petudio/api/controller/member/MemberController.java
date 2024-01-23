package com.nice.petudio.api.controller.member;

import com.nice.petudio.api.controller.member.dto.ChangeNotificationStatusResponse;
import com.nice.petudio.api.controller.member.service.MemberCommandService;
import com.nice.petudio.api.dto.ApiResponse;
import com.nice.petudio.common.auth.auth.Auth;
import com.nice.petudio.common.auth.resolver.MemberId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    @Operation(summary = "[인증] 유저 회원탈퇴")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/member/delete")
    public ApiResponse<?> deleteMember(@MemberId final Long memberId) {
        memberCommandService.deleteMember(memberId);
        return ApiResponse.success();
    }

    @Auth
    @Operation(summary = "[인증] 유저 알림 수신 상태 변경")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/member/notification/status-change")
    public ApiResponse<ChangeNotificationStatusResponse> changeMemberNotificationStatus(@MemberId final Long memberId,
                                                                                        @RequestParam("status") final boolean notificationStatus) {
        return ApiResponse.success(memberCommandService.changeMemberNotificationStatus(memberId, notificationStatus));
    }
}
