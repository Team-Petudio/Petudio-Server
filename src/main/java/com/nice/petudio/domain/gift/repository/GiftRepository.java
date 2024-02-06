package com.nice.petudio.domain.gift.repository;

import com.nice.petudio.domain.gift.Gift;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends GiftRepositoryCustom, JpaRepository<Gift, Long> {
}
