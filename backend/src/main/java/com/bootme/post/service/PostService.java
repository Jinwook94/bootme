package com.bootme.post.service;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.service.AuthService;
import com.bootme.bookmark.repository.PostBookmarkRepository;
import com.bootme.comment.domain.Comment;
import com.bootme.comment.dto.CommentResponse;
import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.member.domain.Member;
import com.bootme.member.service.MemberService;
import com.bootme.post.domain.*;
import com.bootme.post.dto.*;
import com.bootme.comment.repository.CommentRepository;
import com.bootme.post.repository.PostRepository;
import com.bootme.vote.repository.VoteRepository;
import com.bootme.vote.domain.Vote;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bootme.common.exception.ErrorType.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final AuthService authService;
    private final MemberService memberService;
    private final PostRepository postRepository;
    private final PostBookmarkRepository postBookmarkRepository;
    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;
    private final PostTopicPredicate postTopicPredicate;
    private final PostSearchPredicate postSearchPredicate;

    private static final String CREATED_AT = "createdAt";
    private static final String LIKES = "likes";
    private static final String POST = "post";
    private static final String POST_COMMENT = "postComment";
    private static final String NONE = "none";

    @Transactional(readOnly = true)
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_POST, String.valueOf(id)));
    }

    @Transactional
    public Long addPost(AuthInfo authInfo, PostRequest postRequest){
        authService.validateLogin(authInfo);
        Member member = memberService.getMemberById(authInfo.getMemberId());
        Post post = Post.of(postRequest, member);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Transactional(readOnly = true)
    public PostDetailResponse findPost(Long memberId, Long id) {
        boolean isLogin = authService.validateLogin(memberId);
        Post post = getPostById(id);
        return createPostDetailResponse(post, isLogin, memberId);
    }

    private PostDetailResponse createPostDetailResponse(Post post, boolean isLogin, Long memberId) {
        boolean isBookmarked = isLogin && postBookmarkRepository.existsByBookmark_Member_IdAndPost_Id(memberId, post.getId());
        PostDetailResponse response = PostDetailResponse.of(post, isBookmarked);
        updateVoteStatusForPost(isLogin, memberId, post, response);
        return response;
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> findAllPosts(Long memberId, int page, int size, String sort, MultiValueMap<String, String> params) {
        boolean isLogin = authService.validateLogin(memberId);

        Predicate combinedPredicate = getCombinedPredicate(params);

        return getPostPage(page, size, sort, combinedPredicate)
                .map(post -> createPostResponse(post, isLogin, memberId));
    }

    private PostResponse createPostResponse(Post post, boolean isLogin, Long memberId) {
        boolean isBookmarked = isLogin && postBookmarkRepository.existsByBookmark_Member_IdAndPost_Id(memberId, post.getId());
        PostResponse response = PostResponse.of(post, isBookmarked);
        updateVoteStatusForPost(isLogin, memberId, post, response);
        return response;
    }

    @Transactional
    public void modifyPost(AuthInfo authInfo, Long postId, PostRequest postRequest) {
        authService.validateLogin(authInfo);
        Post post = getPostById(postId);

        post.assertAuthor(authInfo.getMemberId());
        post.modifyPost(postRequest);
    }

    @Transactional
    public void deletePost(AuthInfo authInfo, Long postId) {
        authService.validateLogin(authInfo);
        Post post = getPostById(postId);

        post.assertAuthor(authInfo.getMemberId());
        post.softDeletePost();
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findCommentsByPostId(Long memberId, Long postId) {
        boolean isLoggedIn = authService.validateLogin(memberId);

        List<Comment> comments = commentRepository.findByPost_Id(postId);
        List<CommentResponse> commentResponses = mapCommentsToResponse(comments);

        if (isLoggedIn) {
            setVotedForMember(commentResponses, memberId); // 응답 DTO 의 `voted` 필드 값을 설정
        }

        return commentResponses;
    }

    @Transactional
    public void incrementClicks(Long id) {
        Post post = getPostById(id);
        post.incrementClicks();
    }

    @Transactional
    public void incrementBookmarks(Long id){
        Post post = getPostById(id);
        post.incrementBookmarks();
    }

    public Predicate getStatusPredicate() {
        QPost qPost = QPost.post;
        return qPost.status.eq(PostStatus.DISPLAY);
    }

    private Predicate getCombinedPredicate(MultiValueMap<String, String> params) {
        Predicate statusPredicate = getStatusPredicate();
        Predicate topicPredicate = postTopicPredicate.build(params);
        Predicate searchPredicate = postSearchPredicate.build(params);
        return combinePredicates(statusPredicate, topicPredicate, searchPredicate);
    }

    private Predicate combinePredicates(Predicate statusPredicate, Predicate topicPredicate, Predicate searchPredicate) {
        BooleanBuilder builder = new BooleanBuilder();

        if (topicPredicate != null) {
            builder.and(topicPredicate);
        }

        if (searchPredicate != null) {
            builder.and(searchPredicate);
        }

        if (statusPredicate != null) {
            builder.and(statusPredicate);
        }

        return builder.getValue();
    }


    private Page<Post> getPostPage(int page, int size, String sort, Predicate predicate) {
        Pageable pageable = getSortedPageable(page, size, sort);
        if (predicate == null) {
            return postRepository.findAll(pageable);
        } else {
            return postRepository.findAll(predicate, pageable);
        }
    }

    private Pageable getSortedPageable(int page, int size, String sort) {
        Sort sorting;
        if (sort.equals("newest")) {
            sorting = Sort.by(
                    Sort.Order.desc(CREATED_AT),
                    Sort.Order.desc(LIKES)
            );
        } else {
            sorting = Sort.by(
                    Sort.Order.desc(LIKES),
                    Sort.Order.asc(CREATED_AT)
            );
        }
        return PageRequest.of(page-1, size, sorting);
    }

    private List<CommentResponse> mapCommentsToResponse(List<Comment> comments) {
        return comments.stream()
                .sorted(Comparator.comparing(Comment::getGroupNum)
                        .thenComparing(Comment::getOrderNum))
                .map(CommentResponse::of)
                .collect(Collectors.toList());
    }

    // 해당 회원이 댓글에 Upvote or Downvote 했다면 voted 값은 "upvote" or "downvote"
    // 해당 회원이 댓글에 voting 하지 않았다면 voted 값 "none" 입력
    private void setVotedForMember(List<CommentResponse> commentResponses, Long memberId) {
        for (CommentResponse response : commentResponses) {
            Optional<Vote> vote = voteRepository.findByVotableTypeAndVotableIdAndMemberId(POST_COMMENT, response.getId(), memberId);
            response.setVoted(vote.map(Vote::getVoteType).orElse(NONE));
        }
    }

    private void updateVoteStatusForPost(boolean isLogin, Long memberId, Post post, PostResponseDto response) {
        if (isLogin) {
            Optional<Vote> vote = voteRepository.findByVotableTypeAndVotableIdAndMemberId(POST, post.getId(), memberId);
            if(vote.isPresent()) {
                response.setVoted(vote.get().getVoteType());
            } else {
                response.setVoted(NONE);
            }
        }
    }

}
