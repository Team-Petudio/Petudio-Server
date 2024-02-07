package com.nice.petudio.domain.feedlike.repository;

import com.nice.petudio.domain.feedlike.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long>, LikeRepositoryCustom {

}
