package com.nice.petudio.domain.member.point.repository;

import com.nice.petudio.domain.member.point.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends PointRepositoryCustom, JpaRepository<Point, Long> {
}
