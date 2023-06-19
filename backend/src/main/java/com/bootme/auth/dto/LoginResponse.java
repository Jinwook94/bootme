package com.bootme.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {
    Long memberId;
    String email;
    String nickname;
    String profileImage;
    String job;

    @Builder
    public LoginResponse(Long memberId, String email, String nickname, String profileImage, String job) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.job = job;
    }

}
