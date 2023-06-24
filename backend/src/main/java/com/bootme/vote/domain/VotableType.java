package com.bootme.vote.domain;

import java.util.Arrays;

public enum VotableType {

    POST("POST"),
    POST_COMMENT("POST_COMMENT");

    private final String value;

    VotableType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static VotableType fromString(String value) {
        return Arrays.stream(VotableType.values())
                .filter(type -> type.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid VotableType value: " + value));
    }

}
