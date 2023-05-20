package com.bootme.util.fixture;

import com.bootme.webhook.dto.WebhookRequest;

import java.util.HashMap;
import java.util.Map;

public class WebhookFixture {

    public static WebhookRequest getWebhookRequest(int index) {
        index--;
        Long[] sessionIds = {1L, 2L, 3L};
        Long[] memberIds = {1L, 2L, 3L};
        Map<String, String> data = new HashMap<>() {{ put("courseId", "1");}};
        long[] createdAts = {1684335600L, 1684422000L, 1684508400L};

        return WebhookRequest.builder()
                .sessionId(sessionIds[index])
                .memberId(memberIds[index])
                .event("courseClicked")
                .data(data)
                .currentUrl("https://bootme.co.kr/")
                .createdAt(createdAts[index])
                .build();
    }

}
