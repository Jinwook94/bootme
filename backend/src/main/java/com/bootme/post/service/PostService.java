package com.bootme.post.service;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.service.AuthService;
import com.bootme.bookmark.repository.PostBookmarkRepository;
import com.bootme.comment.domain.Comment;
import com.bootme.comment.dto.CommentResponse;
import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.common.util.CustomPageImpl;
import com.bootme.member.domain.Member;
import com.bootme.member.service.MemberService;
import com.bootme.post.domain.*;
import com.bootme.post.dto.*;
import com.bootme.comment.repository.CommentRepository;
import com.bootme.post.repository.PostElasticsearchRepository;
import com.bootme.post.repository.PostRepository;
import com.bootme.post.repository.PostRepositoryProxy;
import com.bootme.session.service.SessionService;
import com.bootme.vote.repository.VoteRepository;
import com.bootme.vote.domain.Vote;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.*;

import static com.bootme.common.exception.ErrorType.*;
import static com.bootme.vote.domain.VotableType.POST;
import static com.bootme.vote.domain.VotableType.POST_COMMENT;
import static com.bootme.vote.domain.VoteType.NONE;

@Service
@RequiredArgsConstructor
public class PostService {

    private final AuthService authService;
    private final MemberService memberService;
    private final SessionService sessionService;
    private final PostRepository postRepository;
    private final PostRepositoryProxy postRepositoryProxy;
    private final PostElasticsearchRepository postElasticsearchRepository;
    private final PostBookmarkRepository postBookmarkRepository;
    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;

    @Transactional(readOnly = true)
    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_POST, String.valueOf(postId)));
    }

    public PostDocument getPostDocumentByPostId(Long postId) {
        return postElasticsearchRepository.findByPostId(postId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_POST, String.valueOf(postId)));
    }

    @Transactional
    @CacheEvict(value = "posts", allEntries = true)
    public Long addPost(AuthInfo authInfo, PostRequest postRequest){
        authService.validateLogin(authInfo);

        Member member = memberService.getMemberById(authInfo.getMemberId());
        Post post = Post.of(postRequest, member);
        Post savedPost = postRepository.save(post);

        PostDocument postDocument = PostDocument.fromPost(savedPost);
        postElasticsearchRepository.save(postDocument);
        return savedPost.getId();
    }

    @Transactional(readOnly = true)
    public PostDetailResponse findPost(Long memberId, Long postId) {
        boolean isLogin = authService.validateLogin(memberId);
        PostDocument postDocument = getPostDocumentByPostId(postId);
        addViewedPost(postId);
        return createPostDetailResponse(postDocument, isLogin, memberId);
    }

    private PostDetailResponse createPostDetailResponse(PostDocument postDocument, boolean isLogin, Long memberId) {
        boolean isBookmarked = isLogin && postBookmarkRepository.existsByBookmark_Member_IdAndPost_Id(memberId, postDocument.getPostId());
        PostDetailResponse response = PostDetailResponse.fromPostDocument(postDocument, isBookmarked);
        updateVoteStatusForPost(isLogin, memberId, response);
        return response;
    }

    @Transactional(readOnly = true)
    public CustomPageImpl<PostResponse> findAllPosts(Long memberId, int page, int size, String sort, MultiValueMap<String, String> params) {
        boolean isLogin = authService.validateLogin(memberId);
        Set<Long> viewedPosts = getViewedPosts();
        String topic = params.getOrDefault("topic", Collections.singletonList("")).get(0);
        String search = params.getOrDefault("search", Collections.singletonList("")).get(0);

        Page<PostResponse> postResponses = postRepositoryProxy.getPostPage(page, size, sort, topic, search)
                .map(postResponse -> createPostResponse(postResponse, isLogin, memberId, viewedPosts));

        return new CustomPageImpl<>(postResponses);
    }

    private PostResponse createPostResponse(PostResponse postResponse, boolean isLogin, Long memberId, Set<Long> viewedPosts) {
        boolean isBookmarked = isLogin && postBookmarkRepository.existsByBookmark_Member_IdAndPost_Id(memberId, postResponse.getId());
        boolean isViewed = viewedPosts.contains(postResponse.getId());
        postResponse.setBookmarked(isBookmarked);
        postResponse.setViewed(isViewed);
        updateVoteStatusForPost(isLogin, memberId, postResponse);
        return postResponse;
    }

    public void addViewedPost(Long postId) {
        sessionService.addViewedPost(postId);
    }

    public Set<Long> getViewedPosts() {
        return sessionService.getViewedPosts();
    }

    @Transactional
    @CacheEvict(value = "posts", allEntries = true)
    public void modifyPost(AuthInfo authInfo, Long postId, PostRequest postRequest) {
        authService.validateLogin(authInfo);

        Post post = getPostById(postId);
        post.assertAuthor(authInfo.getMemberId());
        post.modifyPost(postRequest);

        PostDocument document = getPostDocumentByPostId(postId);
        document.updateFromRequest(postRequest);
        postElasticsearchRepository.modifyPost(document);
    }

    @Transactional
    @CacheEvict(value = "posts", allEntries = true)
    public void deletePost(AuthInfo authInfo, Long postId) {
        authService.validateLogin(authInfo);

        Post post = getPostById(postId);
        post.assertAuthor(authInfo.getMemberId());
        post.softDeletePost();

        PostDocument postDocument = getPostDocumentByPostId(postId);
        postElasticsearchRepository.softDeletePost(postDocument);
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

    private List<CommentResponse> mapCommentsToResponse(List<Comment> comments) {
        return comments.stream()
                .sorted(Comparator.comparing(Comment::getGroupNum)
                        .thenComparing(Comment::getOrderNum))
                .map(CommentResponse::of)
                .toList();
    }

    // 해당 회원이 댓글에 Upvote or Downvote 했다면 voted 값은 "upvote" or "downvote"
    // 해당 회원이 댓글에 voting 하지 않았다면 voted 값 "none" 입력
    private void setVotedForMember(List<CommentResponse> commentResponses, Long memberId) {
        for (CommentResponse response : commentResponses) {
            Optional<Vote> vote = voteRepository.findByVotableTypeAndVotableIdAndMemberId(POST_COMMENT, response.getId(), memberId);
            response.setVoted(vote.map(v -> v.getVoteType().toString()).orElse(NONE.toString()));
        }
    }

    private void updateVoteStatusForPost(boolean isLogin, Long memberId, PostResponseDto response) {
        if (isLogin) {
            Optional<Vote> vote = voteRepository.findByVotableTypeAndVotableIdAndMemberId(POST, response.getId(), memberId);
            if(vote.isPresent()) {
                response.setVoted(vote.get().getVoteType().toString());
            } else {
                response.setVoted(NONE.toString());
            }
        }
    }

}
