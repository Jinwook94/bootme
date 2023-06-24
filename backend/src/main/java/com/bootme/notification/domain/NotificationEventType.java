package com.bootme.notification.domain;

import java.util.Arrays;

public enum NotificationEventType {

    SIGN_UP("SIGN_UP"),
    UPVOTED("UPVOTED"),
    COMMENT_ADDED("COMMENT_ADDED"),
    COMMENT_REPLY_ADDED("COMMENT_REPLY_ADDED"),
    REGISTRATION_START("REGISTRATION_START"),
    REGISTRATION_END_IN_THREE_DAYS("REGISTRATION_END_IN_THREE_DAYS"),
    REGISTRATION_END("REGISTRATION_END");

    private final String value;

    NotificationEventType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static NotificationEventType fromString(String value) {
        return Arrays.stream(NotificationEventType.values())
                .filter(event -> event.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid NotificationEventType value: " + value));
    }
}
