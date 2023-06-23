package com.bootme.vote.repository;

import com.bootme.vote.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByVotableTypeAndVotableIdAndMemberId(String votableType, Long votableId, Long memberId);
}
