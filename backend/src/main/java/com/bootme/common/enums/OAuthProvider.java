package com.bootme.common.enums;

import java.util.Arrays;

public enum OAuthProvider {

    BOOTME("bootme"),
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao");

    private final String value;

    OAuthProvider(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static OAuthProvider fromString(String value) {
        return Arrays.stream(OAuthProvider.values())
                .filter(provider -> provider.toString().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid OAuthProvider value: " + value));
    }

}
