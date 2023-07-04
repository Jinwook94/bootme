package com.bootme.prompt.service;

import com.bootme.prompt.dto.PromptGenerationRequest;

public interface UserInputProcessor {
    String process(PromptGenerationRequest request);
}
