package com.bootme.post.dto;

import com.bootme.post.domain.Post;
import com.bootme.post.domain.PostDocument;
import com.bootme.post.util.PostUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.bootme.common.util.TimeConverter.convertLocalDateTimeToLong;
import static com.bootme.vote.domain.VoteType.NONE;

@Getter
@Setter
public class PostResponse implements PostResponseDto {

    private Long id;
    private Long writerId;
    private String writerNickname;
    private String writerProfileImage;
    private String topic;
    private String title;
    private String contentExcerpt;
    private int likes;
    private int clicks;
    private int bookmarks;
    private String status;
    private long createdAt;
    private long modifiedAt;
    private int commentCount;
    private String voted;
    private boolean isBookmarked;
    private boolean isViewed;

    public PostResponse() {
    }

    @Builder
    public PostResponse(Long id, Long writerId, String writerNickname, String writerProfileImage,
                        String topic, String title, String contentExcerpt, int likes, int clicks,
                        int bookmarks, String status, long createdAt, long modifiedAt, int commentCount,
                        String voted, boolean isBookmarked, boolean isViewed) {
        this.id = id;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.writerProfileImage = writerProfileImage;
        this.topic = topic;
        this.title = title;
        this.contentExcerpt = contentExcerpt;
        this.likes = likes;
        this.clicks = clicks;
        this.bookmarks = bookmarks;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.commentCount = commentCount;
        this.voted = voted;
        this.isBookmarked = isBookmarked;
        this.isViewed = isViewed;
    }

    public static PostResponse fromPost(Post post, boolean isBookmarked, boolean isViewed){
        String excerpt = PostUtils.getContentExcerpt(post.getContent().getValue());
        return PostResponse.builder()
                .id(post.getId())
                .writerId(post.getMember().getId())
                .writerNickname(post.getWriterNickname())
                .writerProfileImage(post.getMember().getProfileImage())
                .topic(post.getTopic())
                .title(post.getTitle().getValue())
                .contentExcerpt(excerpt)
                .likes(post.getLikes())
                .clicks(post.getClicks())
                .bookmarks(post.getBookmarks())
                .status(post.getStatus().toString())
                .createdAt(convertLocalDateTimeToLong(post.getCreatedAt()))
                .modifiedAt(convertLocalDateTimeToLong(post.getModifiedAt()))
                .commentCount(post.getComments().size())
                .isBookmarked(isBookmarked)
                .isViewed(isViewed)
                .build();
    }

    public static PostResponse fromPostDocument(PostDocument postDocument) {
        String excerpt = PostUtils.getContentExcerpt(postDocument.getContent());
        return PostResponse.builder()
                .id(postDocument.getPostId())
                .writerId(postDocument.getWriterId())
                .writerNickname(postDocument.getWriterNickname())
                .writerProfileImage(postDocument.getWriterProfileImage())
                .topic(postDocument.getTopic())
                .title(postDocument.getTitle())
                .contentExcerpt(excerpt)
                .commentCount(postDocument.getCommentCount())
                .likes(postDocument.getLikes())
                .clicks(postDocument.getClicks())
                .bookmarks(postDocument.getBookmarks())
                .status(postDocument.getStatus())
                .createdAt(postDocument.getCreatedAt())
                .modifiedAt(postDocument.getModifiedAt())
                .voted(NONE.toString())
                .isBookmarked(false)
                .isViewed(false)
                .build();
    }

}
