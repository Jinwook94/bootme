package com.bootme.prompt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PromptGenerationResponse {
    private String generatedPrompt;
}
