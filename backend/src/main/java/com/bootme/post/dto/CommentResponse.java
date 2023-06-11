package com.bootme.post.dto;

import com.bootme.post.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.bootme.common.util.TimeConverter.convertLocalDateTimeToLong;

@Getter
@Setter
public class CommentResponse implements VotableResponse{

    private Long id;
    private Long postId;
    private Long writerId;
    private String writerNickname;
    private String writerProfileImage;
    private Long parentId;
    private String content;
    private int groupNum;
    private int levelNum;
    private int orderNum;
    private int likes;
    private String voted;
    private long createdAt;
    private long modifiedAt;

    public CommentResponse() {
    }

    @Builder
    public CommentResponse(Long id, Long postId, Long writerId, String writerNickname, String writerProfileImage, Long parentId,
                           String content, int groupNum, int levelNum, int orderNum, int likes, String voted, long createdAt, long modifiedAt) {
        this.id = id;
        this.postId = postId;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.writerProfileImage = writerProfileImage;
        this.parentId = parentId;
        this.content = content;
        this.groupNum = groupNum;
        this.levelNum = levelNum;
        this.orderNum = orderNum;
        this.likes = likes;
        this.voted = voted;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static CommentResponse of(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .writerId(comment.getMember().getId())
                .writerNickname(comment.getWriterNickname())
                .writerProfileImage(comment.getMember().getProfileImage())
                .parentId(comment.getParentId().orElse(null))
                .content(comment.getContent())
                .groupNum(comment.getGroupNum())
                .levelNum(comment.getLevelNum())
                .orderNum(comment.getOrderNum())
                .likes(comment.getLikes())
                .createdAt(convertLocalDateTimeToLong(comment.getCreatedAt()))
                .modifiedAt(convertLocalDateTimeToLong(comment.getModifiedAt()))
                .build();
    }

}
