package com.bootme.post.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class VoteRequest {

    @NotBlank
    private String votableType;

    @NotNull
    private Long votableId;

    @NotBlank
    private String voteType;

    @NotNull
    private Long memberId;

}
