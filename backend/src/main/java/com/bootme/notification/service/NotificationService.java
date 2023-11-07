package com.bootme.notification.service;

import com.bootme.member.domain.Member;
import com.bootme.notification.domain.Notification;
import com.bootme.comment.dto.CommentNotification;
import com.bootme.notification.domain.NotificationEventType;
import com.bootme.notification.domain.NotificationFactory;
import com.bootme.notification.dto.NotificationResponse;
import com.bootme.notification.repository.NotificationRepository;
import com.bootme.sse.SseService;
import com.bootme.vote.dto.UpvotedNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bootme.sse.SseEvent.NEW_NOTIFICATION;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationFactory notificationFactory;
    private final SseService sseService;

    @Transactional(readOnly = true)
    public List<NotificationResponse> findNotificationsByMemberId(Long memberId) {
        List<Notification> notificationList = notificationRepository.findByMemberId(memberId);
        return notificationList.stream()
                .map(NotificationResponse::of)
                .toList();
    }

    @Transactional
    public void markAllAsCheckedForMember(Long memberId) {
        notificationRepository.markAllAsCheckedForMember(memberId);
    }

    @Transactional
    public void sendNotifications(List<Notification> notifications) {
        notifications.forEach(notification -> {
            notificationRepository.save(notification);
            sseService.emitEventToMember(notification.getMember().getId(), NEW_NOTIFICATION);
        });
    }

    @Transactional
    public void sendNotification(Member member, NotificationEventType event){
        Notification notification = notificationFactory.createSignUpNotification(member, event);
        notificationRepository.save(notification);
        sseService.emitEventToMember(member.getId(), NEW_NOTIFICATION);
    }

    @Transactional
    public void sendNotification(Member member, NotificationEventType event, UpvotedNotification details) {
        Notification notification = notificationFactory.createUpvoteNotification(member, details);
        if (!notificationRepository.existsByMemberAndEventAndMessage(member, event, notification.getMessage())) {
            notificationRepository.save(notification);
        }
        sseService.emitEventToMember(member.getId(), NEW_NOTIFICATION);
    }

    @Transactional
    public void sendNotification(Member member, NotificationEventType event, CommentNotification details) {
        Notification notification = notificationFactory.createCommentNotification(member, event, details);
        notificationRepository.save(notification);
        sseService.emitEventToMember(member.getId(), NEW_NOTIFICATION);
    }

}
