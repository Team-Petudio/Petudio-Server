package com.nice.petudio.domain.member.point.repository;

import com.nice.petudio.domain.member.point.Point;
import java.util.Optional;

public interface PointRepositoryCustom {
    Optional<Point> findByMemberId(Long memberId);
}
