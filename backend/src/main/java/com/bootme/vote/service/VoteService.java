package com.bootme.vote.service;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.service.AuthService;
import com.bootme.bookmark.service.PostBookmarkService;
import com.bootme.comment.service.CommentService;
import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.common.exception.ValidationException;
import com.bootme.member.domain.Member;
import com.bootme.member.service.MemberService;
import com.bootme.comment.domain.Comment;
import com.bootme.post.domain.Post;
import com.bootme.post.domain.PostDocument;
import com.bootme.post.repository.PostElasticsearchRepository;
import com.bootme.vote.domain.Votable;
import com.bootme.post.service.PostService;
import com.bootme.vote.domain.VotableType;
import com.bootme.vote.domain.VoteType;
import com.bootme.vote.domain.Vote;
import com.bootme.comment.dto.CommentResponse;
import com.bootme.post.dto.PostDetailResponse;
import com.bootme.vote.dto.VotableResponse;
import com.bootme.vote.dto.VoteRequest;
import com.bootme.vote.event.UpvotedEvent;
import com.bootme.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static com.bootme.common.exception.ErrorType.*;
import static com.bootme.vote.domain.VoteType.DOWNVOTE;
import static com.bootme.vote.domain.VoteType.UPVOTE;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final AuthService authService;
    private final MemberService memberService;
    private final PostBookmarkService postBookmarkService;
    private final VoteRepository voteRepository;
    private final PostService postService;
    private final PostElasticsearchRepository postElasticsearchRepository;
    private final CommentService commentService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public VotableResponse vote(AuthInfo authInfo, VoteRequest request) {
        authService.validateLogin(authInfo);

        String votableTypeString = request.getVotableType();
        VotableType votableType = VotableType.valueOf(votableTypeString);
        Long votableId = request.getVotableId();
        String voteTypeString = request.getVoteType();
        VoteType voteType = VoteType.valueOf(voteTypeString);
        Member member = memberService.getMemberById(authInfo.getMemberId());

        validateVoteType(voteType);
        Optional<Vote> existingVote = findExistingVote(votableType, votableId, member.getId());

        switch (votableType) {
            case POST -> {
                Post post = postService.getPostById(votableId);
                boolean isBookmarked = postBookmarkService.isBookmarkedByMember(authInfo.getMemberId(), post.getId());
                handleVote(voteType, existingVote, post, votableType, votableId, member);

                PostDocument postDocumentByPostId = postService.getPostDocumentByPostId(post.getId());
                PostDocument postDocumentFromPost = PostDocument.fromPost(post);
                postDocumentFromPost.setId(postDocumentByPostId.getId());
                postElasticsearchRepository.modifyPost(postDocumentFromPost);

                postService.cacheEvict();
                return PostDetailResponse.fromPost(post, isBookmarked);
            }
            case POST_COMMENT -> {
                Comment comment = commentService.getCommentById(votableId);
                handleVote(voteType, existingVote, comment, votableType, votableId, member);
                return CommentResponse.of(comment);
            }
            default -> throw new ValidationException(INVALID_VOTABLE_TYPE, votableType.toString());
        }
    }

    private void validateVoteType(VoteType voteType) {
        if (!Objects.equals(voteType, UPVOTE) && !Objects.equals(voteType, DOWNVOTE)) {
            throw new ValidationException(INVALID_VOTE_TYPE, voteType.toString());
        }
    }

    private Optional<Vote> findExistingVote(VotableType votableType, Long votableId, Long memberId) {
        return voteRepository.findByVotableTypeAndVotableIdAndMemberId(
                votableType, votableId, memberId);
    }

    private <T extends Votable> void handleVote(VoteType voteType, Optional<Vote> existingVote, T votable,
                                                VotableType votableType, Long votableId, Member member) {
        if (existingVote.isPresent()) {
            updateExistingVote(voteType, existingVote.get(), votable);
        } else {
            handleNewVote(voteType, votable, votableType, votableId, member);
        }
    }

    private <T extends Votable> void updateExistingVote(VoteType voteType, Vote existingVote, T votable) {
        if (voteType.equals(existingVote.getVoteType())) {
            undoVote(voteType, votable, existingVote);
        } else {
            changeVote(voteType, votable, existingVote);
        }
    }

    private <T extends Votable> void handleNewVote(VoteType voteType, T votable, VotableType votableType, Long votableId, Member member) {
        if (voteType.equals(UPVOTE)) {
            votable.incrementLikes();
        } else if (voteType.equals(DOWNVOTE)) {
            votable.decrementLikes();
        }
        saveVote(votableType, votableId, voteType, member);
    }

    private <T extends Votable> void undoVote(VoteType voteType, T votable, Vote vote) {
        if (voteType.equals(UPVOTE)) {
            votable.decrementLikes();
        } else if (voteType.equals(DOWNVOTE)) {
            votable.incrementLikes();
        }
        voteRepository.delete(vote);
    }

    private <T extends Votable> void changeVote(VoteType voteType, T votable, Vote vote) {
        if (voteType.equals(UPVOTE)) {
            votable.incrementLikes();
            votable.incrementLikes();
        } else if (voteType.equals(DOWNVOTE)) {
            votable.decrementLikes();
            votable.decrementLikes();
        }
        vote.modifyVoteType(voteType);
        voteRepository.save(vote);
        eventPublisher.publishEvent(new UpvotedEvent(this, vote.getId()));
    }

    private void saveVote(VotableType votableType, Long votableId, VoteType voteType, Member member) {
        Vote vote = Vote.builder()
                .votableType(votableType)
                .votableId(votableId)
                .voteType(voteType)
                .member(member)
                .build();
        voteRepository.save(vote);
        eventPublisher.publishEvent(new UpvotedEvent(this, vote.getId()));
    }

    public Vote getVoteById(Long id) {
        return voteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_VOTE, String.valueOf(id)));
    }

}
