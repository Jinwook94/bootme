package com.bootme.member.dto;

import com.bootme.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {
    private String profileImage;
    private String email;
    private String nickname;
    private String job;

    @Builder
    public MemberResponse(String profileImage, String email, String nickname, String job) {
        this.profileImage = profileImage;
        this.email = email;
        this.nickname = nickname;
        this.job = job;
    }

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .profileImage(member.getProfileImage())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .job(member.getJob())
                .build();
    }

}
