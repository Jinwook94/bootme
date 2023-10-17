package com.bootme.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class CommentRequest {

    @Nullable
    private Long parentId;

    @NotBlank(message = "댓글 내용을 작성해주세요.")
    private String content;

}
