package com.bootme.prompt.controller;

import com.bootme.prompt.dto.*;
import com.bootme.prompt.service.PromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/prompts")
@RequiredArgsConstructor
public class PromptController {

    private final PromptService promptService;

    @PostMapping("/generation/{type}")
    public Mono<ResponseEntity<PromptGenerationResponse>> generatePrompt(@PathVariable String type,
                                                                         @RequestBody @Valid PromptGenerationRequest promptRequest) {
        return promptService.generatePrompt(type, promptRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/submission")
    public Mono<ResponseEntity<PromptSubmissionResponse>> submitPrompt(@RequestBody @Valid PromptSubmissionRequest promptRequest) {
        return promptService.submitPrompt(promptRequest)
                .map(ResponseEntity::ok);
    }

}
