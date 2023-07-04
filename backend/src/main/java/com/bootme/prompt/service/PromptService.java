package com.bootme.prompt.service;

import com.bootme.gpt.dto.GptRequest;
import com.bootme.gpt.service.GptService;
import com.bootme.prompt.dto.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class PromptService {

    private final Map<String, UserInputProcessor> userInputProcessors;
    private final GptService gptService;

    public PromptService(GptService gptService,
                         GeneralUserInputProcessor generalUserInputProcessor,
                         FeatureUserInputProcessor featureUserInputProcessor,
                         ApiDesignUserInputProcessor apiDesignUserInputProcessor) {
        this.gptService = gptService;
        this.userInputProcessors = Map.of(
                "general", generalUserInputProcessor,
                "feature", featureUserInputProcessor,
                "apiDesign", apiDesignUserInputProcessor
        );
    }

    private static final String MODEL = "gpt-3.5-turbo";
    private static final String ROLE_SYSTEM = "system";
    private static final String ROLE_USER = "user";

    private static final String GENERATOR_ROLE = "You are tasked with transforming user inputs into effective prompts for ChatGPT." +
            "Instead of answering the client's questions or inputs, your task is to convert the client's request into a form of prompt optimized for ChatGPT (gpt-3.5-turbo).";
    private static final String GENERATOR_FORMAT = "Please format the output so that it can be directly used as an input to ChatGPT without requiring any further modification.";

    private static final String GENERATOR_SYSTEM_MESSAGE = GENERATOR_ROLE + GENERATOR_FORMAT;
    private static final String SUBMISSION_SYSTEM_MESSAGE = "You are a helpful assistant.";

    public Mono<PromptGenerationResponse> generatePrompt(String type, PromptGenerationRequest request) {
        GptRequest.Message systemMessage = new GptRequest.Message(ROLE_SYSTEM, GENERATOR_SYSTEM_MESSAGE);

        String processedUserInput = userInputProcessors.get(type).process(request);

        GptRequest.Message clientMessage = new GptRequest.Message(ROLE_USER, processedUserInput);
        GptRequest gptRequest = new GptRequest(MODEL, List.of(systemMessage, clientMessage));

        return gptService.sendRequestToOpenAI(gptRequest)
                .map(gptResponse -> new PromptGenerationResponse(gptResponse.getChoices().get(0).getMessage().getContent()));
    }

    public Mono<PromptSubmissionResponse> submitPrompt(PromptSubmissionRequest request) {
        GptRequest.Message systemMessage = new GptRequest.Message(ROLE_SYSTEM, SUBMISSION_SYSTEM_MESSAGE);
        GptRequest.Message clientMessage = new GptRequest.Message(ROLE_USER, request.getPrompt());

        GptRequest gptRequest = new GptRequest(MODEL, List.of(systemMessage, clientMessage));

        return gptService.sendRequestToOpenAI(gptRequest)
                .map(gptResponse -> new PromptSubmissionResponse(gptResponse.getChoices().get(0).getMessage().getContent()));
    }

}
