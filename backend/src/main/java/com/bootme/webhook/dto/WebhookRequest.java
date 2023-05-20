package com.bootme.webhook.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
    public class WebhookRequest {

    private final long sessionId;
    private final long memberId;
    private final String event;
    private final Map<String, String> data;
    private final String currentUrl;
    private final long createdAt;

    @Builder
    public WebhookRequest(long sessionId, long memberId, String event,
                          Map<String, String> data, String currentUrl, long createdAt) {
        this.sessionId = sessionId;
        this.memberId = memberId;
        this.event = event;
        this.data = new ConcurrentHashMap<>(data);
        this.currentUrl = currentUrl;
        this.createdAt = createdAt;
    }

}