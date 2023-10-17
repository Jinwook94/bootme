package com.bootme.gpt.controller;

import com.bootme.gpt.dto.MessageRequest;
import com.bootme.gpt.dto.MessageResponse;
import com.bootme.gpt.service.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/gpt")
@RequiredArgsConstructor
public class GptController {

    private final GptService gptService;

    @PostMapping
    public Mono<MessageResponse> processMessage(@RequestBody MessageRequest gptRequest) {
        return gptService.sendRequestToOpenAI(gptRequest);
    }

}
