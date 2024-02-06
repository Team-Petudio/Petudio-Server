package com.nice.petudio.api.controller.gift;

import com.nice.petudio.api.controller.gift.dto.GiftGenerateResponse;
import com.nice.petudio.api.controller.gift.service.GiftCodeGenerator;
import com.nice.petudio.api.controller.gift.service.GiftCommandService;
import com.nice.petudio.api.controller.gift.service.GiftQueryService;
import com.nice.petudio.api.dto.ApiResponse;
import com.nice.petudio.common.auth.admin.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GiftController {
    private final GiftCommandService giftCommandService;
    private final GiftQueryService giftQueryService;

    @Admin
    @PostMapping("/gift/generate")
    public ApiResponse<GiftGenerateResponse> generateGift() {
        return ApiResponse.success(giftCommandService.generateGift());
    }
}
