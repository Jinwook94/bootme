package com.bootme.notification.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private NotificationEventType event;

    private String message;

    private boolean checked;

    @Builder
    public Notification(Member member, NotificationEventType event, String message) {
        this.member = member;
        this.event = event;
        this.message = message;
        this.checked = false;
    }

}
