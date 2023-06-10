package com.bootme.post.dto;

import com.bootme.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.bootme.common.util.TimeConverter.convertLocalDateTimeToLong;

@Getter
@Setter
public class PostDetailResponse implements VotableResponse, PostResponseDto {

    private Long id;
    private Long memberId;
    private String memberNickname;
    private String memberProfileImage;
    private String topic;
    private String title;
    private String content;
    private int likes;
    private int views;
    private int bookmarks;
    private String status;
    private long createdAt;
    private long modifiedAt;
    private int commentCount;
    private String voted;

    public PostDetailResponse() {
    }

    @Builder
    public PostDetailResponse(Long id, Long memberId, String memberNickname, String memberProfileImage,
                              String topic, String title, String content, int likes, int views, int bookmarks,
                              String status, long createdAt, long modifiedAt, int commentCount, String voted) {
        this.id = id;
        this.memberId = memberId;
        this.memberNickname = memberNickname;
        this.memberProfileImage = memberProfileImage;
        this.topic = topic;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.views = views;
        this.bookmarks = bookmarks;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.commentCount = commentCount;
        this.voted = voted;
    }

    public static PostDetailResponse of(Post post){
        return PostDetailResponse.builder()
                .id(post.getId())
                .memberId(post.getMember().getId())
                .memberNickname(post.getMember().getNickname())
                .memberProfileImage(post.getMember().getProfileImage())
                .topic(post.getTopic())
                .title(post.getTitle().getValue())
                .content(post.getContent().getValue())
                .likes(post.getLikes())
                .views(post.getViews())
                .bookmarks(post.getBookmarks())
                .status(post.getStatus().toString())
                .createdAt(convertLocalDateTimeToLong(post.getCreatedAt()))
                .modifiedAt(convertLocalDateTimeToLong(post.getModifiedAt()))
                .commentCount(post.getComments().size())
                .build();
    }

}