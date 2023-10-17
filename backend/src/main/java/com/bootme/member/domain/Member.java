package com.bootme.member.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.common.exception.AuthenticationException;
import com.bootme.member.dto.UpdateImageRequest;
import com.bootme.notification.domain.Notification;
import com.bootme.stack.domain.Stack;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import static com.bootme.common.exception.ErrorType.MEMBER_ID_MISMATCH;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToOne(fetch = LAZY, cascade = PERSIST)
    @JoinColumn(name = "oauth_info_id")
    private OauthInfo oauthInfo;

    @Column(unique = true, nullable = false)
    private String email;

    private String nickname;

    private String name;

    private String profileImage;

    private String phoneNumber;

    private String job;


    @OneToMany(mappedBy = "member", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private List<MemberStack> memberStacks = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    @Column(nullable = false)
    private Long visitsCount;

    @Builder
    public Member(OauthInfo oauthInfo, String email, String nickname, String name, String profileImage,
                  String phoneNumber, String job, RoleType roleType, Long visitsCount) {
        this.oauthInfo = oauthInfo;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.profileImage = profileImage;
        this.phoneNumber = phoneNumber;
        this.job = job;
        this.roleType = roleType;
        this.visitsCount = visitsCount;
    }

    public static Member of(OauthInfo oauthInfo) {
        return Member.builder()
                .oauthInfo(oauthInfo)
                .email(oauthInfo.getEmail())
                .nickname(oauthInfo.getNickname())
                .name(oauthInfo.getName())
                .profileImage(oauthInfo.getProfileImage())
                .roleType(RoleType.USER)
                .visitsCount(1L)
                .build();
    }

    public void modifyEmail(String email) {
        this.email = email;
    }

    public void modifyProfileImage(UpdateImageRequest request) {
        this.profileImage = request.getProfileImage();
    }

    public void modifyJob(String job) {
        this.job = job;
    }

    public void modifyNickname(String nickname) {
        this.nickname = nickname;
    }

    public void modifyStacks(Set<Stack> newStacks) {
        removeUnmatchedStacksFromMember(newStacks);
        prepareNewStacks(newStacks);
        addNewStacks(newStacks);
    }

    // 기존 스택 중 새로운 스택에는 포함되지 않은 것을 제거
    private void removeUnmatchedStacksFromMember(Set<Stack> newStacks) {
        memberStacks.removeIf(memberStack -> !newStacks.contains(memberStack.getStack()));
    }

    // 새로운 스택 중 기존 스택에 이미 있는 것을 제거
    private void prepareNewStacks(Set<Stack> newStacks) {
        Set<Stack> existingStacks = memberStacks.stream()
                                    .map(MemberStack::getStack)
                                    .collect(Collectors.toSet());
        newStacks.removeAll(existingStacks);
    }

    private void addNewStacks(Set<Stack> newStacks) {
        for (Stack stack : newStacks) {
            MemberStack memberStack = new MemberStack(this, stack);
            memberStacks.add(memberStack);
        }
    }

    public void validateIdMatchesToken(Long authInfoId, Long receivedId) {
        if (!Objects.equals(authInfoId, receivedId)) {
            throw new AuthenticationException(MEMBER_ID_MISMATCH, String.valueOf(receivedId));
        }
    }

}
