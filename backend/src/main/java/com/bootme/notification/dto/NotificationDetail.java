package com.bootme.notification.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NotificationDetail {

    private final String postTitle;
    private final String commentWriter;
    private final String commentContent;

    @Builder
    public NotificationDetail(String postTitle, String commentWriter, String commentContent) {
        this.postTitle = postTitle;
        this.commentWriter = commentWriter;
        this.commentContent = commentContent;
    }

}

