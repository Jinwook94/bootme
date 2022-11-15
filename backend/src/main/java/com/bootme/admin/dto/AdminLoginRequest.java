package com.bootme.admin.dto;

import lombok.Getter;
import javax.validation.constraints.NotBlank;


@Getter
public class AdminLoginRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String id;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    public AdminLoginRequest() {
    }

    public AdminLoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
