package com.bootme.course.domain;

import java.util.Arrays;

public enum CourseStatus {

    DISPLAY("DISPLAY"),
    HIDDEN("HIDDEN"),
    DELETED("DELETED");

    private final String value;

    CourseStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static CourseStatus fromString(String value) {
        return Arrays.stream(CourseStatus.values())
                .filter(status -> status.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid CourseStatus value: " + value));
    }

}
