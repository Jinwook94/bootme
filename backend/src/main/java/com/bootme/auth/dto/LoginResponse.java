package com.bootme.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {
    Long memberId;
    String email;
    String nickName;
    String profileImage;

    @Builder
    public LoginResponse(Long memberId, String email, String nickName, String profileImage) {
        this.memberId = memberId;
        this.email = email;
        this.nickName = nickName;
        this.profileImage = profileImage;
    }

}
