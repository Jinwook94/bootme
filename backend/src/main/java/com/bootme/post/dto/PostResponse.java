package com.bootme.post.dto;

import com.bootme.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.bootme.common.util.TimeConverter.convertLocalDateTimeToLong;

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
    private int views;
    private int bookmarks;
    private String status;
    private long createdAt;
    private long modifiedAt;
    private int commentCount;
    private String voted;

    public PostResponse() {
    }

    @Builder
    public PostResponse(Long id, Long writerId, String writerNickname, String writerProfileImage,
                        String topic, String title, String contentExcerpt, int likes, int views,
                        int bookmarks, String status, long createdAt, long modifiedAt, int commentCount,
                        String voted) {
        this.id = id;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.writerProfileImage = writerProfileImage;
        this.topic = topic;
        this.title = title;
        this.contentExcerpt = contentExcerpt;
        this.likes = likes;
        this.views = views;
        this.bookmarks = bookmarks;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.commentCount = commentCount;
        this.voted = voted;
    }

    public static PostResponse of(Post post){
        return PostResponse.builder()
                .id(post.getId())
                .writerId(post.getMember().getId())
                .writerNickname(post.getWriterNickname())
                .writerProfileImage(post.getMember().getProfileImage())
                .topic(post.getTopic())
                .title(post.getTitle().getValue())
                .contentExcerpt(post.getContent().getExcerpt())
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
