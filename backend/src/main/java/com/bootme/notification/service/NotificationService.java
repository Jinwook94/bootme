package com.bootme.notification.service;

import com.bootme.member.domain.Member;
import com.bootme.notification.domain.Notification;
import com.bootme.comment.dto.CommentNotification;
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

    @Transactional(readOnly = true)
    public List<NotificationResponse> findNotificationsByMemberId(Long memberId){
        List<Notification> notificationList = notificationRepository.findByMemberId(memberId);
        return notificationList.stream().map(NotificationResponse::of).collect(Collectors.toList());
    }

    @Transactional
    public void sendNotification(Member member, String event, UpvotedNotification details){
        Notification notification = Notification.of(member, event, details);
        if (!notificationRepository.existsByMemberAndEventAndMessage(member, event, notification.getMessage())) {
            notificationRepository.save(notification);
        }
    }

    @Transactional
    public void sendNotification(Member member, String event, CommentNotification details){
        Notification notification = Notification.of(member, event, details);
        notificationRepository.save(notification);
    }

    @Transactional
    public Long sendNotification(Member member, String event){
        Notification notification = Notification.of(member, event);
        return notificationRepository.save(notification).getId();
    }

    @Transactional
    public void markAllAsCheckedForMember(Long memberId) {
        notificationRepository.markAllAsCheckedForMember(memberId);
    }

}
