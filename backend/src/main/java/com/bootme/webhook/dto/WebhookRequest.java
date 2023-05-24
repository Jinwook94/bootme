package com.bootme.webhook.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class WebhookRequest {

    private Long sessionId;
    private Long memberId;
    private String event;
    private Map<String, String> data;
    private String currentUrl;
    private long createdAt;

    public WebhookRequest() {}

    @Builder
    public WebhookRequest(Long sessionId, Long memberId, String event,
                          Map<String, String> data, String currentUrl, long createdAt) {
        this.sessionId = sessionId;
        this.memberId = memberId;
        this.event = event;
        this.data = new ConcurrentHashMap<>(data);
        this.currentUrl = currentUrl;
        this.createdAt = createdAt;
    }
}
