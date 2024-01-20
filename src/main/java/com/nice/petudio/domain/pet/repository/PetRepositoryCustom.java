package com.nice.petudio.domain.pet.repository;

import java.util.List;

public interface PetRepositoryCustom {

    List<Long> findIdsByMemberId(Long memberId);
}
