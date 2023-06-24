package com.bootme.vote.domain;

public enum VoteType {

    UPVOTE("UPVOTE"),
    DOWNVOTE("DOWNVOTE"),
    NONE("NONE");

    private final String displayName;

    VoteType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
