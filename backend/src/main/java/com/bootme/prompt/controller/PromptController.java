package com.bootme.prompt.controller;

import com.bootme.prompt.dto.*;
import com.bootme.prompt.service.PromptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/prompts")
@RequiredArgsConstructor
public class PromptController {

    private final PromptService promptService;

    @PostMapping("/generation/{type}")
    public Mono<PromptGenerationResponse> generatePrompt(@PathVariable String type,
                                                         @RequestBody @Valid PromptGenerationRequest promptRequest) {
        return promptService.generatePrompt(type, promptRequest);
    }

    @PostMapping("/submission")
    public Mono<PromptSubmissionResponse> submitPrompt(@RequestBody @Valid PromptSubmissionRequest promptRequest) {
        return promptService.submitPrompt(promptRequest);
    }

}
