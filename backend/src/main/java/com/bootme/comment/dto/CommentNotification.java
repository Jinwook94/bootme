package com.bootme.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentNotification {

    private final String postTitle;
    private final String commentWriter;
    private final String commentContent;

    @Builder
    public CommentNotification(String postTitle, String commentWriter, String commentContent) {
        this.postTitle = postTitle;
        this.commentWriter = commentWriter;
        this.commentContent = commentContent;
    }

}

