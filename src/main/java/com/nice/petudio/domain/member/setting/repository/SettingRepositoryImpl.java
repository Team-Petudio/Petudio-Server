package com.nice.petudio.domain.member.setting.repository;

import static com.nice.petudio.domain.member.setting.QSetting.setting;

import com.nice.petudio.domain.member.setting.Setting;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SettingRepositoryImpl implements SettingRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Setting> findByMemberId(Long memberId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(setting)
                .where(setting.member_id.eq(memberId))
                .fetchOne());
    }
}
