package com.nice.petudio.domain.feedlike.repository;

import com.nice.petudio.domain.feed.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Feed, Long>, LikeRepositoryCustom {
}
