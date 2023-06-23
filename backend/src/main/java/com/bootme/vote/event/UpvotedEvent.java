package com.bootme.vote.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UpvotedEvent extends ApplicationEvent {

    private final Long voteId;

    public UpvotedEvent(Object source, Long voteId) {
        super(source);
        this.voteId = voteId;
    }

}
