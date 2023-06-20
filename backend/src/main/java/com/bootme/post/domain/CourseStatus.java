package com.bootme.post.domain;

public enum CourseStatus {

    DISPLAY("DISPLAY"),
    HIDDEN("HIDDEN"),
    DELETED("DELETED");

    private final String displayName;

    CourseStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
