package com.bootme.comment.domain;

import java.util.Arrays;

public enum CommentStatus {

    DISPLAY("DISPLAY"),
    HIDDEN("HIDDEN"),
    DELETED("DELETED");

    private final String value;

    CommentStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static CommentStatus fromString(String value) {
        return Arrays.stream(CommentStatus.values())
                .filter(status -> status.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid CommentStatus value: " + value));
    }

}
