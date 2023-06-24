package com.bootme.notification.repository;

import com.bootme.member.domain.Member;
import com.bootme.notification.domain.Notification;
import com.bootme.notification.domain.NotificationEventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.member.id = :memberId")
    List<Notification> findByMemberId(@Param("memberId") Long memberId);

    @Transactional
    @Modifying
    @Query("UPDATE Notification n SET n.checked = true WHERE n.member.id = :memberId")
    void markAllAsCheckedForMember(@Param("memberId") Long memberId);

    boolean existsByMemberAndEventAndMessage(Member member, NotificationEventType event, String message);

}
