package com.bootme.member.repository;

import com.bootme.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String uid);

    boolean existsMemberByEmail(String uid);

    boolean existsByNickname(String nickname);

}
