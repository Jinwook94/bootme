package com.bootme.member.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdateProfileRequest {

    private String email;
    private String nickname;
    private String job;
    private List<String> stacks;

    public UpdateProfileRequest() {
    }

    @Builder
    public UpdateProfileRequest(String email, String nickname, String job, List<String> stacks) {
        this.email = email;
        this.nickname = nickname;
        this.job = job;
        this.stacks = stacks;
    }

}
