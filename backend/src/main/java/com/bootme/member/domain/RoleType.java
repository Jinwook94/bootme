package com.bootme.member.domain;

import java.util.Arrays;

public enum RoleType {

    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    RoleType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static RoleType fromString(String value) {
        return Arrays.stream(RoleType.values())
                .filter(role -> role.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid RoleType value: " + value));
    }

}
