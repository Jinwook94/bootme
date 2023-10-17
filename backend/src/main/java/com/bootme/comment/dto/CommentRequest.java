package com.bootme.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nullable;

@Getter
@AllArgsConstructor
public class CommentRequest {

    @Nullable
    private Long parentId;

    @NotBlank(message = "댓글 내용을 작성해주세요.")
    private String content;

}
