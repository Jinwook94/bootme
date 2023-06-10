package com.bootme.post.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PostRequest {

    private String topic;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    private String content;

    @Builder
    public PostRequest(String topic, String title, String content) {
        this.topic = topic;
        this.title = title;
        this.content = content;
    }

}
