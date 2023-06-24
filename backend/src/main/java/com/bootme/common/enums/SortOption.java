package com.bootme.common.enums;

public enum SortOption {

    CREATED_AT("createdAt"),
    CLICKS("clicks"),
    BOOKMARKS("bookmarks"),
    LIKES("likes");

    private final String value;

    SortOption(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
