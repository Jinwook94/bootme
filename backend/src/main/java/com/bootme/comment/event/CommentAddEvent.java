package com.bootme.comment.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CommentAddEvent extends ApplicationEvent {

    private final Long commentId;

    public CommentAddEvent(Object source, Long commentId) {
        super(source);
        this.commentId = commentId;
    }

}
