package com.bootme.notification.domain;

import com.bootme.bookmark.domain.CourseBookmark;
import com.bootme.comment.dto.CommentNotification;
import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.member.domain.Member;
import com.bootme.vote.dto.UpvotedNotification;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.bootme.common.exception.ErrorType.NOT_FOUND_EVENT;
import static com.bootme.notification.domain.NotificationEventType.*;

@Component
public class NotificationFactory {

    public Notification createSignUpNotification(Member member, NotificationEventType event) {
        String message = "환영합니다! 다양한 소프트웨어 커리큘럼들을 비교하고 선택할 수 있도록 도와드릴게요 ☺️";
        return new Notification(member, event, message);
    }

    public Notification createUpvoteNotification(Member member, UpvotedNotification details) {
        String message;
        String memberNickname = "<b>" + details.getMemberNickname() + "</b>";
        String content = details.getContent();
        if (Objects.equals(details.getVotableType(), "post")) {
            message = memberNickname + "님이 <b>\"" + content + "\"</b> 글을 좋아해요.";
        } else if (Objects.equals(details.getVotableType(), "postComment")) {
            if (content == null || content.isEmpty()) {
                message = memberNickname + "님이 댓글을 좋아해요.";
            } else {
                message = memberNickname + "님이 <b>\"" + content + "\"</b> 댓글을 좋아해요.";
            }
        } else {
            throw new ResourceNotFoundException(NOT_FOUND_EVENT);
        }
        return new Notification(member, UPVOTED, message);
    }

    public Notification createCommentNotification(Member member, NotificationEventType event, CommentNotification details) {
        String message;
        switch (event) {
            case COMMENT_ADDED:
                message = "<b>"+ details.getCommentWriter() + "</b>님이 <b>\"" + details.getPostTitle() + "\"</b>에 댓글을 달았어요. " +
                        "<span style='color: #7c7c7c;'>" + details.getCommentContent() + "</span>";
                break;
            case COMMENT_REPLY_ADDED:
                message = "<b>" + details.getCommentWriter() + "</b>님이 대댓글을 달았어요. <span style='color: #7c7c7c;'>" + details.getCommentContent() + "</span>";
                break;
            default:
                throw new ResourceNotFoundException(NOT_FOUND_EVENT, event.toString());
        }
        return new Notification(member, event, message);
    }

    public Notification createCourseBookmarkNotification(Member member, NotificationEventType event, CourseBookmark courseBookmark) {
        String courseTitle = courseBookmark.getCourse().getTitle();

        String message;
        switch (event) {
            case REGISTRATION_START:
                message = "북마크하신 코스 **" + courseTitle + "**의 접수가 시작되었어요. 놓치지 마시고 신청하세요 \uD83D\uDE0A";
                break;
            case REGISTRATION_END_IN_THREE_DAYS:
                message = "북마크하신 코스 **" + courseTitle + "**의 접수 마감이 3일 남았어요. 놓치지 마시고 신청하세요 \uD83D\uDE00";
                break;
            case REGISTRATION_END:
                message = "북마크하신 코스 **" + courseTitle + "**의 접수 마감일이에요. 놓치지 마시고 신청하세요 \uD83D\uDE04";
                break;
            default:
                throw new ResourceNotFoundException(NOT_FOUND_EVENT, event.toString());
        }
        return new Notification(member, event, message);
    }

}

