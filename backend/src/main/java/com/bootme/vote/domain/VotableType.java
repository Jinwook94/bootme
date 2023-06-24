package com.bootme.vote.domain;

public enum VotableType {

    POST("POST"),
    POST_COMMENT("POST_COMMENT");

    private final String displayName;

    VotableType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
