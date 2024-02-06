package com.nice.petudio.domain.feed.repository;

import com.nice.petudio.domain.feed.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long>, FeedRepositoryCustom {
}
