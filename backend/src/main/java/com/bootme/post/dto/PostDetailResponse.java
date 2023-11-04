package com.bootme.post.dto;

import com.bootme.post.domain.Post;
import com.bootme.post.domain.PostDocument;
import com.bootme.vote.dto.VotableResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.bootme.common.util.TimeConverter.convertLocalDateTimeToLong;

@Getter
@Setter
public class PostDetailResponse implements VotableResponse, PostResponseDto {

    private Long id;
    private Long writerId;
    private String writerNickname;
    private String writerProfileImage;
    private String topic;
    private String title;
    private String content;
    private int likes;
    private int clicks;
    private int bookmarks;
    private String status;
    private long createdAt;
    private long modifiedAt;
    private int commentCount;
    private String voted;
    private boolean isBookmarked;

    public PostDetailResponse() {
    }

    @Builder
    public PostDetailResponse(Long id, Long writerId, String writerNickname, String writerProfileImage, String topic,
                              String title, String content, int likes, int clicks, int bookmarks, String status,
                              long createdAt, long modifiedAt, int commentCount, String voted, boolean isBookmarked) {
        this.id = id;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.writerProfileImage = writerProfileImage;
        this.topic = topic;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.clicks = clicks;
        this.bookmarks = bookmarks;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.commentCount = commentCount;
        this.voted = voted;
        this.isBookmarked = isBookmarked;
    }

    public static PostDetailResponse fromPost(Post post, boolean isBookmarked){
        return PostDetailResponse.builder()
                .id(post.getId())
                .writerId(post.getMember().getId())
                .writerNickname(post.getWriterNickname())
                .writerProfileImage(post.getMember().getProfileImage())
                .topic(post.getTopic())
                .title(post.getTitle().getValue())
                .content(post.getContent().getValue())
                .likes(post.getLikes())
                .clicks(post.getClicks())
                .bookmarks(post.getBookmarks())
                .status(post.getStatus().toString())
                .createdAt(convertLocalDateTimeToLong(post.getCreatedAt()))
                .modifiedAt(convertLocalDateTimeToLong(post.getModifiedAt()))
                .commentCount(post.getComments().size())
                .isBookmarked(isBookmarked)
                .build();
    }

    public static PostDetailResponse fromPostDocument(PostDocument postDocument, boolean isBookmarked) {
        return PostDetailResponse.builder()
                .id(postDocument.getPostId())
                .writerId(postDocument.getWriterId())
                .writerNickname(postDocument.getWriterNickname())
                .writerProfileImage(postDocument.getWriterProfileImage())
                .topic(postDocument.getTopic())
                .title(postDocument.getTitle())
                .content(postDocument.getContent())
                .likes(postDocument.getLikes())
                .clicks(postDocument.getClicks())
                .bookmarks(postDocument.getBookmarks())
                .status(postDocument.getStatus())
                .createdAt(postDocument.getCreatedAt())
                .modifiedAt(postDocument.getModifiedAt())
                .commentCount(postDocument.getCommentCount())
                .isBookmarked(isBookmarked)
                .build();
    }

}
