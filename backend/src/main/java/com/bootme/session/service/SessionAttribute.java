package com.bootme.session.service;

import java.util.Arrays;

public enum SessionAttribute {

    VIEWED_POSTS("viewedPosts");

    private final String value;

    SessionAttribute(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static SessionAttribute fromString(String value) {
        return Arrays.stream(SessionAttribute.values())
                .filter(attribute -> attribute.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid SessionAttribute value: " + value));
    }

}

