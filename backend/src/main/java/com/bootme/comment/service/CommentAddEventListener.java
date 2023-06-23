package com.bootme.comment.service;

import com.bootme.comment.domain.Comment;
import com.bootme.comment.event.CommentAddEvent;
import com.bootme.member.domain.Member;
import com.bootme.notification.dto.NotificationDetail;
import com.bootme.notification.service.NotificationService;
import com.bootme.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component
@RequiredArgsConstructor
public class CommentAddEventListener {

    private final NotificationService notificationService;
    private final CommentService commentService;

    private static final String COMMENT_ADDED_EVENT = "commentAdded";
    private static final String COMMENT_REPLY_ADDED_EVENT = "commentReplyAdded";

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handleCommentAddEvent(CommentAddEvent event) {
        Comment comment = commentService.getCommentById(event.getCommentId());
        Comment parent = comment.getParent();
        Post post = comment.getPost();
        Member commentWriter = comment.getMember();

        NotificationDetail details = NotificationDetail.builder()
                .postTitle(post.getTitle().getValue())
                .commentWriter(commentWriter.getNickname())
                .commentContent(comment.getContent())
                .build();

        if (!post.isWriterOfPost(commentWriter)) {
            notificationService.sendNotification(post.getMember(), COMMENT_ADDED_EVENT, details);
        }

        if (parent != null && !parent.isWriterOfComment(commentWriter)) {
            notificationService.sendNotification(parent.getMember(), COMMENT_REPLY_ADDED_EVENT, details);
        }
    }

}
