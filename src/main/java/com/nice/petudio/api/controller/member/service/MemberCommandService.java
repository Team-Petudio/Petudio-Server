package com.nice.petudio.api.controller.member.service;

import com.nice.petudio.api.controller.member.dto.ChangeNotificationStatusResponse;
import com.nice.petudio.api.controller.member.dto.CreateMemberRequest;
import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.point.Point;
import com.nice.petudio.domain.member.point.repository.PointRepository;
import com.nice.petudio.domain.member.repository.MemberRepository;
import com.nice.petudio.domain.member.setting.Setting;
import com.nice.petudio.domain.member.setting.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandService {
    private final MemberRepository memberRepository;
    private final PointRepository pointRepository;
    private final SettingRepository settingRepository;

    public Long registerMember(CreateMemberRequest request) {
        MemberServiceUtils.validateNotExistsMember(memberRepository, request.getSocialId(), request.getSocialType());

        Member member = memberRepository.save(
                Member.newInstance(request.getSocialId(), request.getSocialType(), request.getFcmToken()));
        pointRepository.save(Point.fromMemberId(member.getId()));
        settingRepository.save(Setting.fromMemberId(member.getId()));

        return member.getId();
    }

    public ChangeNotificationStatusResponse changeMemberNotificationStatus(final Long memberId,
                                                                           final boolean notificationStatus) {
        Setting setting = SettingServiceUtils.findSettingByMemberId(settingRepository, memberId);
        setting.changeNotificationStatus(notificationStatus);

        return new ChangeNotificationStatusResponse(notificationStatus);
    }

    public void deleteMember(final Long memberId) {
        Member member = MemberServiceUtils.findMemberById(memberRepository, memberId);
        Setting setting = SettingServiceUtils.findSettingByMemberId(settingRepository, memberId);
        Point point = PointServiceUtils.findPointByMemberId(pointRepository, memberId);

        memberRepository.delete(member);
        settingRepository.delete(setting);
        pointRepository.delete(point);
    }
}
