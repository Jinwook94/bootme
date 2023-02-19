package com.bootme.webhook.dto;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class WebhookRequest {

    private long sessionId;
    private long memberId;
    private String event;
    private final Map<String, String> data = new ConcurrentHashMap<>();
    private String url;
    private long createdAt;

}