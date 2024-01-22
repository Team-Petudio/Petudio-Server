package com.nice.petudio.api.controller.member.service;

import com.nice.petudio.domain.member.setting.Setting;
import com.nice.petudio.domain.member.setting.repository.SettingRepository;
import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.NotFoundException;

public class SettingServiceUtils {

    public static Setting findSettingByMemberId(SettingRepository settingRepository, final Long memberId) {
        return settingRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorCode.NOT_FOUND_MEMBER_INFO_EXCEPTION,
                        String.format("memberId(%d)의 Setting 정보를 찾을 수 없습니다.", memberId)));
    }
}
