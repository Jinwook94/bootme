package com.bootme.vote.dto;

import lombok.Builder;
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

    @Builder
    public VoteRequest(String votableType, Long votableId, String voteType, Long memberId) {
        this.votableType = votableType;
        this.votableId = votableId;
        this.voteType = voteType;
        this.memberId = memberId;
    }

}
