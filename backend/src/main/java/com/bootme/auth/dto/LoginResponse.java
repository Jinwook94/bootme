package com.bootme.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {
    Long memberId;
    String email;
    String nickname;
    String profileImage;

    @Builder
    public LoginResponse(Long memberId, String email, String nickname, String profileImage) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

}
