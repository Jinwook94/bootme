package com.bootme.vote.domain;

import java.util.Arrays;

public enum VoteType {

    UPVOTE("UPVOTE"),
    DOWNVOTE("DOWNVOTE"),
    NONE("NONE");

    private final String value;

    VoteType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static VoteType fromString(String value) {
        return Arrays.stream(VoteType.values())
                .filter(vote -> vote.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid VoteType value: " + value));
    }
}
