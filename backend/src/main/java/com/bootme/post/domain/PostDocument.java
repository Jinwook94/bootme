package com.bootme.post.domain;

import com.bootme.post.dto.PostRequest;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.HashMap;
import java.util.Map;

import static com.bootme.common.util.TimeConverter.*;

@Data
@Document(indexName = "post")
public class PostDocument {

    @Id
    private String id;

    @Field(type = FieldType.Long, name = "postId")
    private Long postId;

    @Field(type = FieldType.Long, name = "memberId")
    private Long writerId;

    @Field(type = FieldType.Text, name = "writerNickname")
    private String writerNickname;

    @Field(type = FieldType.Text, name = "writerProfileImage")
    private String writerProfileImage;

    @Field(type = FieldType.Text, name = "topic")
    private String topic;

    @Field(type = FieldType.Text, name = "title")
    private String title;

    @Field(type = FieldType.Text, name = "content")
    private String content;

    @Field(type = FieldType.Integer, name = "commentCount")
    private int commentCount;

    @Field(type = FieldType.Integer, name = "likes")
    private int likes;

    @Field(type = FieldType.Integer, name = "clicks")
    private int clicks;

    @Field(type = FieldType.Integer, name = "bookmarks")
    private int bookmarks;

    @Field(type = FieldType.Keyword, name = "status")
    private String status;

    @Field(type = FieldType.Long, name = "createdAt")
    private long createdAt;

    @Field(type = FieldType.Long, name = "modifiedAt")
    private long modifiedAt;

    @Builder
    public PostDocument(String id, Long postId, Long writerId, String writerNickname, String writerProfileImage, String topic,
                        String title, String content, int commentCount, int likes, int clicks, int bookmarks,
                        String status, long createdAt, long modifiedAt) {
        this.id = id;
        this.postId = postId;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.writerProfileImage = writerProfileImage;
        this.topic = topic;
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.likes = likes;
        this.clicks = clicks;
        this.bookmarks = bookmarks;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static PostDocument fromPost(Post post) {
        return PostDocument.builder()
                .postId(post.getId())
                .writerId(post.getMember().getId())
                .writerNickname(post.getWriterNickname())
                .writerProfileImage(post.getMember().getProfileImage())
                .topic(post.getTopic())
                .title(post.getTitle().getValue())
                .content(post.getContent().getValue())
                .commentCount(post.getComments().size())
                .likes(post.getLikes())
                .clicks(post.getClicks())
                .bookmarks(post.getBookmarks())
                .status(post.getStatus().name())
                .createdAt(convertLocalDateTimeToLong(post.getCreatedAt()))
                .modifiedAt(convertLocalDateTimeToLong(post.getModifiedAt()))
                .build();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", this.id);
        map.put("postId", this.postId);
        map.put("writerId", this.writerId);
        map.put("writerNickname", this.writerNickname);
        map.put("writerProfileImage", this.writerProfileImage);
        map.put("topic", this.topic);
        map.put("title", this.title);
        map.put("content", this.content);
        map.put("commentCount", this.commentCount);
        map.put("likes", this.likes);
        map.put("clicks", this.clicks);
        map.put("bookmarks", this.bookmarks);
        map.put("status", this.status);
        map.put("createdAt", this.createdAt);
        map.put("modifiedAt", this.modifiedAt);
        return map;
    }

    public void updateFromRequest(PostRequest postRequest) {
        this.setTopic(postRequest.getTopic());
        this.setTitle(postRequest.getTitle());
        this.setContent(postRequest.getContent());
        this.setModifiedAt(System.currentTimeMillis());
    }

}
