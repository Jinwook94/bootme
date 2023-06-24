package com.bootme.notification.controller;

import com.bootme.notification.dto.NotificationResponse;
import com.bootme.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/notifications/{memberId}")
    public ResponseEntity<List<NotificationResponse>> findNotificationsByMemberId(@PathVariable Long memberId) {
        List<NotificationResponse> notificationResponseList = notificationService.findNotificationsByMemberId(memberId);
        return ResponseEntity.ok().body(notificationResponseList);
    }

    @PutMapping("/notifications/{memberId}/checked")
    public ResponseEntity<List<NotificationResponse>> markAllNotificationsAsCheckedForMember(@PathVariable Long memberId) {
        notificationService.markAllAsCheckedForMember(memberId);
        List<NotificationResponse> notificationResponseList = notificationService.findNotificationsByMemberId(memberId);
        return ResponseEntity.ok().body(notificationResponseList);
    }

}
