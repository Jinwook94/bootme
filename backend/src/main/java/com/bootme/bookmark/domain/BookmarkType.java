package com.bootme.bookmark.domain;

import java.util.Arrays;

public enum BookmarkType {

    COURSE("COURSE"),
    POST("POST"),
    COMMENT("COMMENT");

    private final String value;

    BookmarkType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static BookmarkType fromString(String value) {
        return Arrays.stream(BookmarkType.values())
                .filter(type -> type.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid BookmarkType value: " + value));
    }

}
