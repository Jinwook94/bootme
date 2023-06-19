package com.bootme.post.controller;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.util.Login;
import com.bootme.post.dto.VotableResponse;
import com.bootme.post.dto.VoteRequest;
import com.bootme.post.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/vote")
    public ResponseEntity<VotableResponse> vote(@Login AuthInfo authInfo,
                                           @Valid VoteRequest request) {
        VotableResponse votableResponse = voteService.vote(authInfo, request);
        return ResponseEntity.ok(votableResponse);
    }

}
