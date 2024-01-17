package com.nice.petudio.domain.member.setting.repository;

import com.nice.petudio.domain.member.setting.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting, Long> {
}
