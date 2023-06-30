package com.bootme.prompt.service;

import com.bootme.gpt.dto.GptRequest;
import com.bootme.gpt.service.GptService;
import com.bootme.prompt.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromptService {

    private final GptService gptService;

    private static final String MODEL = "gpt-3.5-turbo";
    private static final String ROLE_SYSTEM = "system";
    private static final String ROLE_USER = "user";

    private static final String ROLE = "You are assigned the role of a prompt generator for the gpt-3.5-turbo version of ChatGPT." +
            "Instead of answering the client's questions or inputs, your task is to convert the client's request into a form of prompt optimized for ChatGPT (gpt-3.5-turbo).";
    private static final String OPTIMIZATION = "The prompt you return should be optimized for ChatGPT to answer.";
    private static final String FORMAT = "The generated prompt should be formatted such that it can be directly copied and input into the ChatGPT system without requiring any additional modifications.";

    private static final String SYSTEM_MESSAGE_GENERATOR = ROLE;
    private static final String SYSTEM_MESSAGE_SUBMISSION = "You are a helpful assistant.";

    public Mono<PromptGenerationResponse> generatePrompt(String type, PromptGenerationRequest request) {
        GptRequest.Message systemMessage = new GptRequest.Message(ROLE_SYSTEM, SYSTEM_MESSAGE_GENERATOR);
        GptRequest.Message clientMessage;

        switch (type) {
            case "general":
                clientMessage = new GptRequest.Message(ROLE_USER, request.toString());
                break;
            case "feature":
                clientMessage = new GptRequest.Message(ROLE_USER, request.toString());
                break;
            case "apiDesign":
                clientMessage = new GptRequest.Message(ROLE_USER, request.toString());
                break;
            default:
                throw new IllegalArgumentException("Invalid prompt type: " + type);
        }

        GptRequest gptRequest = new GptRequest(MODEL, List.of(systemMessage, clientMessage));

        return gptService.sendRequestToOpenAI(gptRequest)
                .map(gptResponse -> new PromptGenerationResponse(gptResponse.getChoices().get(0).getMessage().getContent()));
    }

    public Mono<PromptSubmissionResponse> submitPrompt(PromptSubmissionRequest request) {
        GptRequest.Message systemMessage = new GptRequest.Message(ROLE_SYSTEM, SYSTEM_MESSAGE_SUBMISSION);
        GptRequest.Message clientMessage = new GptRequest.Message(ROLE_USER, request.getPrompt());

        GptRequest gptRequest = new GptRequest(MODEL, List.of(systemMessage, clientMessage));

        return gptService.sendRequestToOpenAI(gptRequest)
                .map(gptResponse -> new PromptSubmissionResponse(gptResponse.getChoices().get(0).getMessage().getContent()));
    }

}
