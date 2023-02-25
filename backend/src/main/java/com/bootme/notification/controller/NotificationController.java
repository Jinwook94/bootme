package com.bootme.notification.controller;

import com.bootme.member.domain.Member;
import com.bootme.member.service.MemberService;
import com.bootme.notification.dto.NotificationResponse;
import com.bootme.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    public final NotificationService notificationService;
    public final MemberService memberService;

    @GetMapping("/notifications/{memberId}")
    public ResponseEntity<List<NotificationResponse>> findNotificationsByMemberId(@PathVariable Long memberId) {
        List<NotificationResponse> notificationResponseList = notificationService.findByMemberId(memberId);
        return ResponseEntity.ok().body(notificationResponseList);
    }

    @PostMapping("/notifications/{memberId}")
    public ResponseEntity<Void> sendNotification(@PathVariable Long memberId,
                                                 @RequestParam String event) {
        Member member = memberService.findById(memberId);
        Long notificationId = notificationService.sendNotification(member, event);
        return ResponseEntity.created(URI.create("/notifications/" + notificationId)).build();
    }

    @PutMapping("/notifications/{memberId}/checked")
    public ResponseEntity<List<NotificationResponse>> markAllNotificationsAsCheckedForMember(@PathVariable Long memberId) {
        notificationService.markAllAsCheckedForMember(memberId);
        List<NotificationResponse> notificationResponseList = notificationService.findByMemberId(memberId);
        return ResponseEntity.ok().body(notificationResponseList);
    }

}
