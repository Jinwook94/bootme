package com.bootme.vote.dto;

import lombok.Getter;

@Getter
public class PostUpvotedNotification implements UpvotedNotification {

    private final String votableType;
    private final String memberNickname;
    private final String content;

    public PostUpvotedNotification(String memberNickname, String content) {
        this.votableType = "post";
        this.memberNickname = memberNickname;
        this.content = content;
    }

}
