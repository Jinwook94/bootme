package com.bootme.common.enums;

import com.bootme.common.exception.AuthenticationException;

import java.util.Objects;

import static com.bootme.common.exception.ErrorType.INVALID_ISSUER;

public enum JwtIssuer {

    BOOTME("bootme"),
    GOOGLE("google"),
    KAKAO("kakao");

    private final String value;
    public static final String BOOTME_ISSUER = "bootme.co.kr";
    private static final String GOOGLE_ISSUER = "https://accounts.google.com";
    private static final String KAKAO_ISSUER = "https://kauth.kakao.com";

    JwtIssuer(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static JwtIssuer fromString(String issuer) {
        if (Objects.equals(issuer, BOOTME_ISSUER)) {
            return BOOTME;
        } else if (Objects.equals(issuer, GOOGLE_ISSUER)) {
            return GOOGLE;
        } else if (Objects.equals(issuer, KAKAO_ISSUER)) {
            return KAKAO;
        }
        throw new AuthenticationException(INVALID_ISSUER, issuer);
    }

}
