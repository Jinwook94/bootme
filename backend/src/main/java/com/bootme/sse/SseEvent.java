package com.bootme.sse;

import java.util.Arrays;

public enum SseEvent {

    CONNECT("connect"),
    NEW_NOTIFICATION("newNotification");

    private final String value;

    SseEvent(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static SseEvent fromString(String value) {
        return Arrays.stream(SseEvent.values())
                .filter(event -> event.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid SseEvent value: " + value));
    }

}
