package com.nice.petudio.api.controller.member.service;

import com.nice.petudio.api.controller.album.service.AlbumServiceUtils;
import com.nice.petudio.api.controller.member.dto.ChangeNotificationStatusResponse;
import com.nice.petudio.api.controller.member.dto.CreateMemberRequest;
import com.nice.petudio.api.controller.pet.service.PetServiceUtils;
import com.nice.petudio.domain.album.Album;
import com.nice.petudio.domain.album.repository.AlbumRepository;
import com.nice.petudio.domain.member.Member;
import com.nice.petudio.domain.member.point.Point;
import com.nice.petudio.domain.member.point.repository.PointRepository;
import com.nice.petudio.domain.member.repository.MemberRepository;
import com.nice.petudio.domain.member.setting.Setting;
import com.nice.petudio.domain.member.setting.repository.SettingRepository;
import com.nice.petudio.domain.pet.repository.PetRepository;
import java.util.List;
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
    private final PetRepository petRepository;
    private final AlbumRepository albumRepository;

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
        deleteMemberBasicInfo(memberId);
        deletePetsWithoutAiProfileByMemberId(memberId);
    }

    private void deleteMemberBasicInfo(Long memberId) {
        Member member = MemberServiceUtils.findMemberById(memberRepository, memberId);
        Setting setting = SettingServiceUtils.findSettingByMemberId(settingRepository, memberId);
        Point point = PointServiceUtils.findPointByMemberId(pointRepository, memberId);
        memberRepository.delete(member);
        settingRepository.delete(setting);
        pointRepository.delete(point);
    }

    // AI 프로필이 없는 애완동물 데이터 삭제
    private void deletePetsWithoutAiProfileByMemberId(Long memberId) {
        List<Long> memberPetIds = PetServiceUtils.findPetsByMemberId(petRepository, memberId);
        List<Album> memberAlbums = AlbumServiceUtils.findAlbumsByMemberId(albumRepository, memberId);

        for (Album memberAlbum : memberAlbums) {
            Long petId = memberAlbum.getPet_id();
            if(!memberPetIds.contains(petId)) {
                petRepository.deleteById(petId);
            }
        }
    }
}
