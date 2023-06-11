package com.bootme.post.service;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.service.AuthService;
import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.member.domain.Member;
import com.bootme.member.service.MemberService;
import com.bootme.post.domain.Comment;
import com.bootme.post.domain.Post;
import com.bootme.post.dto.CommentRequest;
import com.bootme.post.dto.CommentResponse;
import com.bootme.post.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bootme.common.exception.ErrorType.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final AuthService authService;
    private final PostService postService;
    private final MemberService memberService;
    private final CommentRepository commentRepository;

    @Transactional
    public Long addComment(AuthInfo authInfo, Long postId, CommentRequest commentRequest) {
        authService.validateLogin(authInfo);
        Post post = postService.getPostById(postId);
        Member member = memberService.getMemberById(authInfo.getMemberId());

        Comment comment = createComment(commentRequest, post, member);
        Comment savedComment = commentRepository.save(comment);
        return savedComment.getId();
    }

    @Transactional(readOnly = true)
    public CommentResponse findComment(AuthInfo authInfo, Long id) {
        authService.validateLogin(authInfo);
        Comment comment = getCommentById(id);
        comment.validateWriter(authInfo.getMemberId(), id);

        return CommentResponse.of(comment);
    }

    @Transactional
    public void modifyComment(AuthInfo authInfo, Long id, String modifiedContent) {
        authService.validateLogin(authInfo);
        Comment comment = getCommentById(id);
        comment.validateWriter(authInfo.getMemberId(), comment.getMember().getId());

        comment.modifyContent(modifiedContent);
    }

    @Transactional
    public void deleteComment(AuthInfo authInfo, Long id){
        authService.validateLogin(authInfo);
        Comment comment = getCommentById(id);
        comment.validateWriter(authInfo.getMemberId(), comment.getMember().getId());

        comment.softDeleteComment();
    }

    private Comment createComment(CommentRequest commentRequest, Post post, Member member){
        Comment parent = null;
        if (commentRequest.getParentId() != null) {
            parent = getCommentById(commentRequest.getParentId());
        }

        return Comment.builder()
                .post(post)
                .member(member)
                .parent(parent)
                .content(commentRequest.getContent())
                .groupNum(calculateGroup(parent, post))
                .levelNum(calculateLevel(parent))
                .orderNum(calculateOrder(parent))
                .build();
    }

    public Comment getCommentById(Long id){
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_COMMENT, String.valueOf(id)));
    }

    private int calculateGroup(Comment parent, Post post) {
        if (parent != null) {
            return parent.getGroupNum();
        }
        return commentRepository.findMaxGroupByPost(post.getId()) + 1;
    }

    private int calculateLevel(Comment parent) {
        if (parent != null) {
            return parent.getLevelNum() + 1;
        }
        return 0;
    }

    private int calculateOrder(Comment parent) {
        if (parent != null) {
            return parent.getChildren().size();
        }
        return 0;
    }
}
