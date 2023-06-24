package com.bootme.notification.dto;

import com.bootme.notification.domain.Notification;
import lombok.Builder;
import lombok.Getter;

import static com.bootme.common.util.TimeConverter.convertLocalDateTimeToLong;

@Getter
public class NotificationResponse {

    private Long notificationId;
    private Long memberId;
    private String event;
    private String message;
    private boolean isChecked;
    private long createdAt;

    public NotificationResponse(){
    }

    @Builder
    public NotificationResponse(Long notificationId, Long memberId, String event, String message,
                                boolean isChecked, long createdAt) {
        this.notificationId = notificationId;
        this.memberId = memberId;
        this.event = event;
        this.message = message;
        this.isChecked = isChecked;
        this.createdAt = createdAt;
    }

    public static NotificationResponse of(Notification notification){
        return NotificationResponse.builder()
                .notificationId(notification.getId())
                .memberId(notification.getMember().getId())
                .event(notification.getEvent().toString())
                .message(notification.getMessage())
                .isChecked(notification.isChecked())
                .createdAt(convertLocalDateTimeToLong(notification.getCreatedAt()))
                .build();
    }

}
