package com.nice.petudio.api.controller.member.service;

import com.nice.petudio.domain.member.point.Point;
import com.nice.petudio.domain.member.point.repository.PointRepository;
import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.NotFoundException;

public class PointServiceUtils {

    public static Point findPointByMemberId(PointRepository pointRepository, final Long memberId) {
        return pointRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundException(
                        ErrorCode.NOT_FOUND_MEMBER_INFO_EXCEPTION,
                        String.format("memberId(%d)의 Point 정보를 찾을 수 없습니다.", memberId)));
    }
}
