package com.bootme.member.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.stack.domain.Stack;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member_stack")
public class MemberStack extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_stack_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stack_id")
    private Stack stack;

    @Builder
    public MemberStack(Member member, Stack stack) {
        this.member = member;
        this.stack = stack;
    }

    public static MemberStack of(Member member, Stack stack) {
        return MemberStack.builder()
                .member(member)
                .stack(stack)
                .build();
    }

}
