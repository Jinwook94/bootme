package com.bootme.notification.service;

import com.bootme.member.domain.Member;
import com.bootme.notification.domain.Notification;
import com.bootme.comment.dto.CommentNotification;
import com.bootme.notification.domain.NotificationEventType;
import com.bootme.notification.domain.NotificationFactory;
import com.bootme.notification.dto.NotificationResponse;
import com.bootme.notification.repository.NotificationRepository;
import com.bootme.vote.dto.UpvotedNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationFactory notificationFactory;

    @Transactional(readOnly = true)
    public List<NotificationResponse> findNotificationsByMemberId(Long memberId) {
        List<Notification> notificationList = notificationRepository.findByMemberId(memberId);
        return notificationList.stream().map(NotificationResponse::of).collect(Collectors.toList());
    }

    @Transactional
    public void markAllAsCheckedForMember(Long memberId) {
        notificationRepository.markAllAsCheckedForMember(memberId);
    }

    @Transactional
    public void sendNotifications(List<Notification> notifications) {
        notificationRepository.saveAll(notifications);
    }

    @Transactional
    public void sendNotification(Member member, NotificationEventType event){
        Notification notification = notificationFactory.createSignUpNotification(member, event);
        notificationRepository.save(notification);
    }

    @Transactional
    public void sendNotification(Member member, NotificationEventType event, UpvotedNotification details) {
        Notification notification = notificationFactory.createUpvoteNotification(member, details);
        if (!notificationRepository.existsByMemberAndEventAndMessage(member, event, notification.getMessage())) {
            notificationRepository.save(notification);
        }
    }

    @Transactional
    public void sendNotification(Member member, NotificationEventType event, CommentNotification details) {
        Notification notification = notificationFactory.createCommentNotification(member, event, details);
        notificationRepository.save(notification);
    }

}
