package com.bootme.gpt.service;

import com.bootme.common.exception.ExternalServiceException;
import com.bootme.config.OpenAiConfig;
import com.bootme.gpt.dto.GptRequest;
import com.bootme.gpt.dto.GptResponse;
import com.bootme.gpt.dto.MessageRequest;
import com.bootme.gpt.dto.MessageResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

import static com.bootme.common.exception.ErrorType.OPEN_AI_API_FAIL;

@Service
public class GptService {

    private final OpenAiConfig openAiConfig;
    private final WebClient webClient;

    private static final String OPENAI_API_ENDPOINT = "https://api.openai.com/v1/chat/completions";
    private static final String MODEL = "gpt-3.5-turbo";
    private static final String ROLE_SYSTEM = "system";
    private static final String ROLE_USER = "user";
    private static final String SYSTEM_MESSAGE = "You are a helpful assistant.";

    public GptService(OpenAiConfig openAiConfig) {
        this.openAiConfig = openAiConfig;
        this.webClient = WebClient.builder()
                .baseUrl(OPENAI_API_ENDPOINT)
                .defaultHeader("Authorization", "Bearer " + this.openAiConfig.getApiKey())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<MessageResponse> sendRequestToOpenAI(MessageRequest request) {
        GptRequest.Message systemMessage = new GptRequest.Message(ROLE_SYSTEM, SYSTEM_MESSAGE);
        GptRequest.Message clientMessage = new GptRequest.Message(ROLE_USER, request.getMessage());

        GptRequest gptRequest = new GptRequest(MODEL, List.of(systemMessage, clientMessage));

        return this.webClient.post()
                .bodyValue(gptRequest)
                .retrieve()
                .bodyToMono(GptResponse.class)
                .timeout(Duration.ofSeconds(120))
                .onErrorMap(e -> new ExternalServiceException(OPEN_AI_API_FAIL, e))
                .map(gptResponse -> new MessageResponse(gptResponse.getChoices().get(0).getMessage().getContent()));
    }

    public Mono<GptResponse> sendRequestToOpenAI(GptRequest request) {
        return this.webClient.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GptResponse.class)
                .timeout(Duration.ofSeconds(120))
                .onErrorMap(e -> new ExternalServiceException(OPEN_AI_API_FAIL, e));
    }

}
