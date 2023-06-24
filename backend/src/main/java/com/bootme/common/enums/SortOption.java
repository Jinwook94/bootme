package com.bootme.common.enums;

import java.util.Arrays;

public enum SortOption {

    CREATED_AT("createdAt"),
    CLICKS("clicks"),
    BOOKMARKS("bookmarks"),
    LIKES("likes");

    private final String value;

    SortOption(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static SortOption fromString(String value) {
        return Arrays.stream(SortOption.values())
                .filter(option -> option.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid SortOption value: " + value));
    }

}
