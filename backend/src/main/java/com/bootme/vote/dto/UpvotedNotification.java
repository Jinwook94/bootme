package com.bootme.vote.dto;

import lombok.Getter;

@Getter
public class UpvotedNotification {

    private final String memberNickname;
    private final String postTitle;

    public UpvotedNotification(String memberNickname, String postTitle) {
        this.memberNickname = memberNickname;
        this.postTitle = postTitle;
    }

}
