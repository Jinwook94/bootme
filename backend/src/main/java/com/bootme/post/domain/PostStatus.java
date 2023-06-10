package com.bootme.post.domain;

public enum PostStatus {
    DISPLAY("DISPLAY"),
    HIDDEN("HIDDEN"),
    DELETED("DELETED");

    private final String displayName;

    PostStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
