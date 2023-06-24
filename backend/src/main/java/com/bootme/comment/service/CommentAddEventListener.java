package com.bootme.comment.service;

import com.bootme.comment.domain.Comment;
import com.bootme.comment.event.CommentAddEvent;
import com.bootme.member.domain.Member;
import com.bootme.comment.dto.CommentNotification;
import com.bootme.notification.service.NotificationService;
import com.bootme.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.bootme.notification.domain.NotificationEventType.COMMENT_ADDED;
import static com.bootme.notification.domain.NotificationEventType.COMMENT_REPLY_ADDED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component
@RequiredArgsConstructor
public class CommentAddEventListener {

    private final NotificationService notificationService;
    private final CommentService commentService;

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handleCommentAddEvent(CommentAddEvent event) {
        Comment comment = commentService.getCommentById(event.getCommentId());
        Comment parent = comment.getParent();
        Post post = comment.getPost();
        Member commentWriter = comment.getMember();

        CommentNotification details = CommentNotification.builder()
                .postTitle(post.getTitle().getValue())
                .commentWriter(commentWriter.getNickname())
                .commentContent(comment.getContent())
                .build();

        if (!post.isWriterOfPost(commentWriter)) {
            notificationService.sendNotification(post.getMember(), COMMENT_ADDED, details);
        }

        if (parent != null && !parent.isWriterOfComment(commentWriter)) {
            notificationService.sendNotification(parent.getMember(), COMMENT_REPLY_ADDED, details);
        }
    }

}
