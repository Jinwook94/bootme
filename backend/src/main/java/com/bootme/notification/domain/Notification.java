package com.bootme.notification.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.member.domain.BookmarkCourse;
import com.bootme.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.bootme.common.exception.ErrorType.NOT_FOUND_EVENT;
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

    private String event;

    private String message;

    private boolean checked;

    @Builder
    public Notification(Member member, String event, String message, boolean checked) {
        this.member = member;
        this.event = event;
        this.message = message;
        this.checked = checked;
    }

    public static Notification of(Member member, String event){
        String message;
        switch (event) {
            case "signUp":
                message = "환영합니다! 다양한 소프트웨어 커리큘럼들을 비교하고 선택할 수 있도록 도와드릴게요 :) \uD83D\uDE0A \uD83D\uDE00 \uD83D\uDE04 ☺️";
                break;
            default:
                throw new ResourceNotFoundException(NOT_FOUND_EVENT, event);
        }
        return Notification.builder()
                .member(member)
                .event(event)
                .message(message)
                .checked(false)
                .build();
    }

    public static Notification of(Member member, String event, Bookmark bookmark){
        String courseTitle = bookmark.getCourse().getTitle();

        String message;
        switch (event) {
            case "registrationStart":
                message = "북마크하신 코스 **" + courseTitle + "**의 접수가 시작되었어요. 놓치지 마시고 신청하세요 \uD83D\uDE0A";
                break;
            case "registrationEndInThreeDays":
                message = "북마크하신 코스 **" + courseTitle + "**의 접수 마감이 3일 남았어요. 놓치지 마시고 신청하세요 \uD83D\uDE00";
                break;
            case "registrationEnd":
                message = "북마크하신 코스 **" + courseTitle + "**의 접수 마감일이에요. 놓치지 마시고 신청하세요 \uD83D\uDE04";
                break;
            default:
                throw new ResourceNotFoundException(NOT_FOUND_EVENT, event);
        }
        return Notification.builder()
                .member(member)
                .event(event)
                .message(message)
                .checked(false)
                .build();
    }

}
