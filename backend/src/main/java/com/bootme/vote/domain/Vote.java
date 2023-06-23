package com.bootme.vote.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long id;

    private String votableType;

    private Long votableId;

    private String voteType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @Builder
    public Vote(String votableType, Long votableId, String voteType, Member member) {
        this.votableType = votableType;
        this.votableId = votableId;
        this.voteType = voteType;
        this.member = member;
    }

    public void modifyVoteType(String voteType) {
        this.voteType = voteType;
    }
}
