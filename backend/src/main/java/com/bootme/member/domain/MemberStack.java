package com.bootme.member.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.stack.domain.Stack;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member_stack")
public class MemberStack extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_stack_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "stack_id")
    private Stack stack;

    @Builder
    public MemberStack(Member member, Stack stack) {
        this.member = member;
        this.stack = stack;
    }

}
