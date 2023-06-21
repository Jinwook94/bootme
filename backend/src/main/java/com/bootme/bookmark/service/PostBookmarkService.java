package com.bootme.bookmark.service;

import com.bootme.bookmark.domain.Bookmark;
import com.bootme.bookmark.domain.PostBookmark;
import com.bootme.bookmark.repository.PostBookmarkRepository;
import com.bootme.common.exception.ConflictException;
import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.post.domain.Post;
import com.bootme.member.service.MemberService;
import com.bootme.member.domain.Member;
import com.bootme.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.bootme.bookmark.domain.BookmarkType.POST;
import static com.bootme.common.exception.ErrorType.ALREADY_BOOKMARKED;
import static com.bootme.common.exception.ErrorType.NOT_FOUND_BOOKMARK;

@Service
@RequiredArgsConstructor
public class PostBookmarkService {

    private final PostBookmarkRepository postBookmarkRepository;
    private final MemberService memberService;
    private final PostService postService;

    @Transactional
    public Long addPostBookmark(Long memberId, Long postId) {
        boolean isExist = isPostBookmarkExist(memberId, postId);

        if (isExist){
            throw new ConflictException(ALREADY_BOOKMARKED, "memberId=" + memberId + ", postId="+postId);
        }

        Member foundMember = memberService.getMemberById(memberId);
        Post foundPost = postService.getPostById(postId);

        Bookmark bookmark = new Bookmark(POST, foundMember);
        PostBookmark postBookmark = new PostBookmark(bookmark, foundPost);

        return postBookmarkRepository.save(postBookmark).getId();
    }

    private boolean isPostBookmarkExist(Long memberId, Long postId) {
        return postBookmarkRepository.existsByBookmark_Member_IdAndPost_Id(memberId, postId);
    }

    @Transactional
    public void deletePostBookmark(Long memberId, Long postId) {
        PostBookmark postBookmark = postBookmarkRepository.findAll().stream()
                .filter(cb -> cb.getBookmark().getType() == POST)
                .filter(cb -> Objects.equals(cb.getBookmark().getMember().getId(), memberId))
                .filter(cb -> cb.getPost().getId().equals(postId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_BOOKMARK,
                        "memberId=" + memberId + ", postId="+postId));

        postBookmarkRepository.delete(postBookmark);
    }

}
