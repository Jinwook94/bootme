package com.bootme.vote.service;

import com.bootme.member.domain.Member;
import com.bootme.notification.service.NotificationService;
import com.bootme.post.domain.Post;
import com.bootme.post.service.PostService;
import com.bootme.vote.domain.Vote;
import com.bootme.vote.dto.UpvotedNotification;
import com.bootme.vote.event.UpvotedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Objects;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component
@RequiredArgsConstructor
public class UpvotedEventListener {

    private final NotificationService notificationService;
    private final VoteService voteService;
    private final PostService postService;

    private static final String POST = "post";
    private static final String UPVOTE = "upvote";
    private static final String UPVOTED_EVENT = "upvoted";

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @Transactional(propagation = REQUIRES_NEW)
    public void handleUpvotedEvent(UpvotedEvent event) {
        Vote vote = voteService.getVoteById(event.getVoteId());
        Post post = postService.getPostById(vote.getVotableId());

        if (shouldSendNotification(vote, post)) {
            sendNotification(vote, post);
        }
    }

    private boolean shouldSendNotification(Vote vote, Post post) {
        return isNotSelfVote(vote, post.getMember())
                && isVoteForPost(vote)
                && isUpvote(vote);
    }

    private boolean isNotSelfVote(Vote vote, Member postMember) {
        return !vote.isVoteToSelf(postMember);
    }

    private boolean isVoteForPost(Vote vote) {
        return Objects.equals(vote.getVotableType(), POST);
    }

    private boolean isUpvote(Vote vote) {
        return Objects.equals(vote.getVoteType(), UPVOTE);
    }

    private void sendNotification(Vote vote, Post post) {
        String voterNickname = vote.getMember().getNickname();
        String postTitle = post.getTitle().getValue();
        UpvotedNotification details = new UpvotedNotification(voterNickname, postTitle);

        notificationService.sendNotification(post.getMember(), UPVOTED_EVENT, details);
    }

}

