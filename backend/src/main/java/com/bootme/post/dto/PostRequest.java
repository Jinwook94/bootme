package com.bootme.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostRequest {

    @NotBlank(message = "토픽을 선택해주세요.")
    private final String topic;

    @NotBlank(message = "제목을 입력해주세요.")
    private final String title;

    private final String content;

    @Builder
    public PostRequest(String topic, String title, String content) {
        this.topic = topic;
        this.title = title;
        this.content = content;
    }

}
