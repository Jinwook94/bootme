package com.bootme.vote.dto;

import lombok.Getter;

@Getter
public class CommentUpvotedNotification implements UpvotedNotification {

    private final String votableType;
    private final String memberNickname;
    private final String content;

    public CommentUpvotedNotification(String memberNickname, String content) {
        this.votableType = "postComment";
        this.memberNickname = memberNickname;
        this.content = content;
    }

}
