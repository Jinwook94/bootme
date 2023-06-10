package com.bootme.auth.dto;

import lombok.Getter;

@Getter
public class AuthInfo {

    private final Long memberId;
    private final String roleType;
    private final String nickname;

    public AuthInfo(Long memberId, String roleType, String nickname) {
        this.memberId = memberId;
        this.roleType = roleType;
        this.nickname = nickname;
    }

}
