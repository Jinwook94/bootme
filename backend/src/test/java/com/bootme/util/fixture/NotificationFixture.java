package com.bootme.util.fixture;

import com.bootme.notification.dto.NotificationResponse;

public class NotificationFixture {

    public static NotificationResponse getNotificationResponse(int index) {
        index--;
        Long[] notificationIds = {1L, 2L, 3L};
        Long[] memberIds = {1L, 2L, 3L};
        long[] createdAt = {1684335600L, 1684422000L, 1684508400L};

        return NotificationResponse.builder()
                .notificationId(notificationIds[index])
                .memberId(memberIds[index])
                .event("signUp")
                .message("환영합니다! 다양한 소프트웨어 커리큘럼들을 비교하고 선택할 수 있도록 도와드릴게요 :) \uD83D\uDE0A \uD83D\uDE00 \uD83D\uDE04 ☺️")
                .createdAt(createdAt[index])
                .build();
    }

}
