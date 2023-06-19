package com.bootme.member.dto;

import com.bootme.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfileResponse {
    private String profileImage;
    private String email;
    private String nickname;
    private String job;
    private String[] stacks;

    @Builder
    public ProfileResponse(String profileImage, String email, String nickname, String job, String[] stacks) {
        this.profileImage = profileImage;
        this.email = email;
        this.nickname = nickname;
        this.job = job;
        this.stacks = stacks;
    }

    public static ProfileResponse of(Member member, String[] stacks) {
        return ProfileResponse.builder()
                .profileImage(member.getProfileImage())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .job(member.getJob())
                .stacks(stacks)
                .build();
    }

}
