package com.nice.petudio.domain.pet.repository;

import com.nice.petudio.domain.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends PetRepositoryCustom, JpaRepository<Pet, Long> {
}
