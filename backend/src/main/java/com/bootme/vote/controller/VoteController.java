package com.bootme.vote.controller;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.util.Login;
import com.bootme.vote.dto.VotableResponse;
import com.bootme.vote.dto.VoteRequest;
import com.bootme.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/vote")
    public ResponseEntity<VotableResponse> vote(@Login AuthInfo authInfo,
                                           @Valid @RequestBody VoteRequest request) {
        VotableResponse votableResponse = voteService.vote(authInfo, request);
        return ResponseEntity.ok(votableResponse);
    }

}
