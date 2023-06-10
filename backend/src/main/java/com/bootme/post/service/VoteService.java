package com.bootme.post.service;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.service.AuthService;
import com.bootme.common.exception.ValidationException;
import com.bootme.member.domain.Member;
import com.bootme.member.service.MemberService;
import com.bootme.post.domain.Comment;
import com.bootme.post.domain.Post;
import com.bootme.post.domain.Votable;
import com.bootme.post.domain.Vote;
import com.bootme.post.dto.CommentResponse;
import com.bootme.post.dto.PostDetailResponse;
import com.bootme.post.dto.VotableResponse;
import com.bootme.post.dto.VoteRequest;
import com.bootme.post.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static com.bootme.common.exception.ErrorType.INVALID_VOTABLE_TYPE;
import static com.bootme.common.exception.ErrorType.INVALID_VOTE_TYPE;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final AuthService authService;
    private final MemberService memberService;
    private final VoteRepository voteRepository;
    private final PostService postService;
    private final CommentService commentService;


    private static final String UPVOTE = "upvote";
    private static final String DOWNVOTE = "downvote";
    private static final String POST = "post";
    private static final String POST_COMMENT = "postComment";


    @Transactional
    public VotableResponse vote(AuthInfo authInfo, VoteRequest request) {
        authService.validateLogin(authInfo);
        String votableType = request.getVotableType();
        Long votableId = request.getVotableId();
        String voteType = request.getVoteType();
        Member member = memberService.getMemberById(authInfo.getMemberId());

        authService.validateLogin(authInfo);
        validateVoteType(voteType);
        Optional<Vote> existingVote = findExistingVote(votableType, votableId, member.getId());

        switch (votableType) {
            case POST:
                Post post = postService.getPostById(votableId);
                handleVote(voteType, existingVote, post, votableType, votableId, member);
                return PostDetailResponse.of(post);
            case POST_COMMENT:
                Comment comment = commentService.getCommentById(votableId);
                handleVote(voteType, existingVote, comment, votableType, votableId, member);
                return CommentResponse.of(comment);
            default:
                throw new ValidationException(INVALID_VOTABLE_TYPE, votableType);
        }
    }

    private void validateVoteType(String voteType) {
        if (!Objects.equals(voteType, UPVOTE) && !Objects.equals(voteType, DOWNVOTE)) {
            throw new ValidationException(INVALID_VOTE_TYPE, voteType);
        }
    }

    private Optional<Vote> findExistingVote(String votableType, Long votableId, Long memberId) {
        return voteRepository.findByVotableTypeAndVotableIdAndMemberId(
                votableType, votableId, memberId);
    }

    private <T extends Votable> void handleVote(String voteType, Optional<Vote> existingVote, T votable,
                                                String votableType, Long votableId, Member member) {
        if (existingVote.isPresent()) {
            updateExistingVote(voteType, existingVote.get(), votable);
        } else {
            handleNewVote(voteType, votable, votableType, votableId, member);
        }
    }

    private <T extends Votable> void updateExistingVote(String voteType, Vote existingVote, T votable) {
        if (voteType.equals(existingVote.getVoteType())) {
            undoVote(voteType, votable, existingVote);
        } else {
            changeVote(voteType, votable, existingVote);
        }
    }

    private <T extends Votable> void handleNewVote(String voteType, T votable, String votableType, Long votableId, Member member) {
        if (voteType.equals(UPVOTE)) {
            votable.incrementLikes();
        } else if (voteType.equals(DOWNVOTE)) {
            votable.decrementLikes();
        }
        saveVote(votableType, votableId, voteType, member);
    }

    private <T extends Votable> void undoVote(String voteType, T votable, Vote vote) {
        if (voteType.equals(UPVOTE)) {
            votable.decrementLikes();
        } else if (voteType.equals(DOWNVOTE)) {
            votable.incrementLikes();
        }
        voteRepository.delete(vote);
    }

    private <T extends Votable> void changeVote(String voteType, T votable, Vote vote) {
        if (voteType.equals(UPVOTE)) {
            votable.incrementLikes();
            votable.incrementLikes();
        } else if (voteType.equals(DOWNVOTE)) {
            votable.decrementLikes();
            votable.decrementLikes();
        }
        vote.modifyVoteType(voteType);
        voteRepository.save(vote);
    }

    private void saveVote(String votableType, Long votableId, String voteType, Member member) {
        Vote vote = Vote.builder()
                .votableType(votableType)
                .votableId(votableId)
                .voteType(voteType)
                .member(member)
                .build();
        voteRepository.save(vote);
    }

}
