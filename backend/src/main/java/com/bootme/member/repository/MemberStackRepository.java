package com.bootme.member.repository;

import com.bootme.member.domain.MemberStack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberStackRepository extends JpaRepository<MemberStack, Long> {
    List<MemberStack> findByMember_Id(Long memberId);
}

