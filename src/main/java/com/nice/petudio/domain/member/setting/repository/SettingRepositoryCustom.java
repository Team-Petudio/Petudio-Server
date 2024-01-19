package com.nice.petudio.domain.member.setting.repository;

import com.nice.petudio.domain.member.setting.Setting;
import java.util.Optional;

public interface SettingRepositoryCustom {
    Optional<Setting> findByMemberId(Long memberId);
}
