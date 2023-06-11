package com.bootme.post.domain;

public enum CommentStatus {
    DISPLAY("DISPLAY"),
    HIDDEN("HIDDEN"),
    DELETED("DELETED");

    private final String displayName;

    CommentStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
