package com.nice.petudio.domain.gift.repository;

import com.nice.petudio.domain.concept.Concept;
import com.nice.petudio.domain.gift.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends GiftRepositoryCustom, JpaRepository<Gift, Long> {
}
