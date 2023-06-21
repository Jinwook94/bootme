package com.bootme.bookmark.domain;

public enum BookmarkType {

    COURSE("COURSE"),
    POST("POST"),
    COMMENT("COMMENT");

    private final String displayName;

    BookmarkType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
