package com.nice.petudio.domain.member.repository;

import com.nice.petudio.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends MemberRepositoryCustom, JpaRepository<Member, Long> {
}
