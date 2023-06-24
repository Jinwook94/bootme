package com.bootme.vote.service;

import com.bootme.comment.domain.Comment;
import com.bootme.comment.service.CommentService;
import com.bootme.notification.service.NotificationService;
import com.bootme.post.domain.Post;
import com.bootme.post.service.PostService;
import com.bootme.vote.domain.Vote;
import com.bootme.vote.dto.CommentUpvotedNotification;
import com.bootme.vote.dto.PostUpvotedNotification;
import com.bootme.vote.event.UpvotedEvent;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Objects;

import static com.bootme.notification.domain.NotificationEventType.UPVOTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component
@RequiredArgsConstructor
public class UpvotedEventListener {

    private final NotificationService notificationService;
    private final VoteService voteService;
    private final PostService postService;
    private final CommentService commentService;

    private static final String POST = "post";
    private static final String COMMENT = "postComment";
    private static final String UPVOTE = "upvote";

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handleUpvotedEvent(UpvotedEvent event) {
        Vote vote = voteService.getVoteById(event.getVoteId());

        if (isUpvote(vote)) {
            if (isVoteForPost(vote)) {
                Post post = postService.getPostById(vote.getVotableId());
                if (!vote.isVoteToSelf(post.getMember())) {
                    sendPostUpvoteNotification(vote, post);
                }
            } else if (isVoteForComment(vote)) {
                Comment comment = commentService.getCommentById(vote.getVotableId());
                if (!vote.isVoteToSelf(comment.getMember())) {
                    sendCommentUpvoteNotification(vote, comment);
                }
            }
        }
    }

    private boolean isVoteForPost(Vote vote) {
        return Objects.equals(vote.getVotableType(), POST);
    }

    private boolean isVoteForComment(Vote vote) {
        return Objects.equals(vote.getVotableType(), COMMENT);
    }

    private boolean isUpvote(Vote vote) {
        return Objects.equals(vote.getVoteType(), UPVOTE);
    }

    private void sendPostUpvoteNotification(Vote vote, Post post) {
        String voterNickname = vote.getMember().getNickname();
        String postTitle = post.getTitle().getValue();
        PostUpvotedNotification details = new PostUpvotedNotification(voterNickname, postTitle);

        notificationService.sendNotification(post.getMember(), UPVOTED, details);
    }

    private void sendCommentUpvoteNotification(Vote vote, Comment comment) {
        String voterNickname = vote.getMember().getNickname();
        String commentContent = comment.getContent();
        commentContent = Jsoup.parse(commentContent).text();
        CommentUpvotedNotification details = new CommentUpvotedNotification(voterNickname, commentContent);

        notificationService.sendNotification(comment.getMember(), UPVOTED, details);
    }

}
