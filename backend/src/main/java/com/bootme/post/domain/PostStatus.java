package com.bootme.post.domain;

import java.util.Arrays;

public enum PostStatus {

    DISPLAY("DISPLAY"),
    HIDDEN("HIDDEN"),
    DELETED("DELETED");

    private final String value;

    PostStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static PostStatus fromString(String value) {
        return Arrays.stream(PostStatus.values())
                .filter(status -> status.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid PostStatus value: " + value));
    }

}
