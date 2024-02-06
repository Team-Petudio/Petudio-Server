package com.nice.petudio.api.controller.gift;

import com.nice.petudio.api.controller.gift.dto.GiftGenerateResponse;
import com.nice.petudio.api.controller.gift.service.GiftCodeGenerator;
import com.nice.petudio.api.controller.gift.service.GiftCommandService;
import com.nice.petudio.api.controller.gift.service.GiftQueryService;
import com.nice.petudio.api.dto.ApiResponse;
import com.nice.petudio.common.auth.admin.Admin;
import com.nice.petudio.common.auth.auth.Auth;
import com.nice.petudio.common.auth.resolver.MemberId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GiftController {
    private final GiftCommandService giftCommandService;
    private final GiftQueryService giftQueryService;

    @Operation(summary = "[관리자] 기프트 생성")
    @Admin
    @PostMapping("/gift/generate")
    public ApiResponse<GiftGenerateResponse> generateGift(@MemberId final Long memberId) {
        return ApiResponse.success(giftCommandService.generateGift(memberId));
    }

    @Operation(summary = "[인증] 기프트 사용")
    @Auth
    @DeleteMapping("/gift/use")
    public ApiResponse<?> useGift(@MemberId final Long memberId, @RequestParam final String giftCode) {
        giftCommandService.useGift(memberId, giftCode);
        return ApiResponse.success();
    }
}
