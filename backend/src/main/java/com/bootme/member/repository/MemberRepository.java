package com.bootme.member.repository;

import com.bootme.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String uid);

    boolean existsMemberByEmail(String uid);

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.visitsCount = m.visitsCount + 1, m.modifiedAt = CURRENT_TIMESTAMP WHERE m.email = :email")
    int incrementVisits(@Param("email") String email);

}
