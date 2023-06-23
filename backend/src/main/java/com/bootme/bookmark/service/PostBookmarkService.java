package com.bootme.bookmark.service;

import com.bootme.bookmark.domain.Bookmark;
import com.bootme.bookmark.domain.BookmarkType;
import com.bootme.bookmark.domain.PostBookmark;
import com.bootme.bookmark.repository.BookmarkRepository;
import com.bootme.bookmark.repository.PostBookmarkRepository;
import com.bootme.common.exception.ConflictException;
import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.post.domain.Post;
import com.bootme.post.domain.Vote;
import com.bootme.post.dto.PostResponse;
import com.bootme.member.service.MemberService;
import com.bootme.member.domain.Member;
import com.bootme.post.repository.VoteRepository;
import com.bootme.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bootme.common.exception.ErrorType.ALREADY_BOOKMARKED;
import static com.bootme.common.exception.ErrorType.NOT_FOUND_BOOKMARK;

@Service
@RequiredArgsConstructor
public class PostBookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PostBookmarkRepository postBookmarkRepository;
    private final VoteRepository voteRepository;
    private final MemberService memberService;
    private final PostService postService;

    private static final String NONE = "none";
    private static final String POST = "post";

    @Transactional
    public Long addPostBookmark(Long memberId, Long postId) {
        boolean isBookmarked = isBookmarkedByMember(memberId, postId);

        if (isBookmarked){
            throw new ConflictException(ALREADY_BOOKMARKED, "memberId=" + memberId + ", postId="+postId);
        }

        Member foundMember = memberService.getMemberById(memberId);
        Post foundPost = postService.getPostById(postId);

        Bookmark bookmark = new Bookmark(BookmarkType.POST, foundMember);
        PostBookmark postBookmark = new PostBookmark(bookmark, foundPost);

        return postBookmarkRepository.save(postBookmark).getId();
    }

    public boolean isBookmarkedByMember(Long memberId, Long postId) {
        return postBookmarkRepository.existsByBookmark_Member_IdAndPost_Id(memberId, postId);
    }

    @Transactional
    public void deletePostBookmark(Long memberId, Long postId) {
        PostBookmark postBookmark = postBookmarkRepository.findByBookmark_Member_IdAndPost_Id(memberId, postId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_BOOKMARK,
                        "memberId=" + memberId + ", postId="+postId));

        postBookmarkRepository.delete(postBookmark);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getBookmarkedPosts(Long memberId, Pageable pageable) {
        List<PostBookmark> postBookmarks = findPostBookmarksByMemberId(memberId);
        List<PostResponse> postResponses = mapPostBookmarksToPostResponse(postBookmarks);

        return getPageablePostResponse(pageable, postResponses);
    }

    private List<PostBookmark> findPostBookmarksByMemberId(Long memberId) {
        return bookmarkRepository.findByMember_Id(memberId).stream()
                .filter(bookmark -> bookmark.getType() == BookmarkType.POST)
                .flatMap(bookmark -> postBookmarkRepository.findByBookmark_Id(bookmark.getId()).stream())
                .collect(Collectors.toList());
    }

    private List<PostResponse> mapPostBookmarksToPostResponse(List<PostBookmark> postBookmarks) {
        return postBookmarks.stream()
                .map(postBookmark -> {
                    Post post = postBookmark.getPost();
                    PostResponse postResponse = PostResponse.of(post, true);
                    updateVoteStatusForPost(postBookmark.getBookmark().getMember().getId(), post, postResponse);
                    return postResponse;
                })
                .collect(Collectors.toList());
    }

    private void updateVoteStatusForPost(Long memberId, Post post, PostResponse response) {
        Optional<Vote> vote = voteRepository.findByVotableTypeAndVotableIdAndMemberId(POST, post.getId(), memberId);
        if(vote.isPresent()) {
            response.setVoted(vote.get().getVoteType());
        } else {
            response.setVoted(NONE);
        }
    }

    private Page<PostResponse> getPageablePostResponse(Pageable pageable, List<PostResponse> postResponses) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), postResponses.size());
        return new PageImpl<>(postResponses.subList(start, end), pageable, postResponses.size());
    }

}