package com.bootme.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthInfo {

    private Long id;
    private String role;
    private String nickname;

}
