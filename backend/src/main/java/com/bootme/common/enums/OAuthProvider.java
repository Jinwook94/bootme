package com.bootme.common.enums;

public enum OAuthProvider {

    BOOTME("bootme"),
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao");

    private final String value;

    OAuthProvider(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static OAuthProvider fromString(String value) {
        for (OAuthProvider provider : OAuthProvider.values()) {
            if (provider.getValue().equalsIgnoreCase(value)) {
                return provider;
            }
        }
        throw new IllegalArgumentException("Invalid OAuthProvider value: " + value);
    }

}
