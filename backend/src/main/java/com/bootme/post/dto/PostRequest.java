package com.bootme.post.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonCreator
    public PostRequest(
            @JsonProperty("topic") String topic,
            @JsonProperty("title") String title,
            @JsonProperty("content") String content) {
        this.topic = topic;
        this.title = title;
        this.content = content;
    }

}
