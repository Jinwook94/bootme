package com.bootme.notification.service;

import com.bootme.member.domain.Member;
import com.bootme.notification.domain.Notification;
import com.bootme.notification.dto.NotificationResponse;
import com.bootme.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional(readOnly = true)
    public List<NotificationResponse> findNotificationsByMemberId(Long memberId){
        List<Notification> notificationList = notificationRepository.findByMemberId(memberId);
        return notificationList.stream().map(NotificationResponse::of).collect(Collectors.toList());
    }

    public Long sendNotification(Member member, String event){
        Notification notification = Notification.of(member, event);
        return notificationRepository.save(notification).getId();
    }

    public void markAllAsCheckedForMember(Long memberId) {
        notificationRepository.markAllAsCheckedForMember(memberId);
    }

}
