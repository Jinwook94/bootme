package com.bootme.util.fixture;

import com.bootme.prompt.dto.PromptGenerationRequest;
import com.bootme.prompt.dto.PromptGenerationResponse;
import com.bootme.prompt.dto.PromptSubmissionRequest;
import com.bootme.prompt.dto.PromptSubmissionResponse;

import java.util.List;

public class PromptFixture {

    private static final String VALID_PROMPT_TYPE_1 = "general";
    private static final String VALID_PROMPT_TYPE_2 = "feature";
    private static final String VALID_PROMPT_TYPE_3 = "apiDesign";

    private static final String VALID_FEATURE_DESCRIPTION_1 = "스프링 부트 프로젝트에서 알림 기능 구현하기 위한 Entity 클래스 작성";

    public static final String VALID_STACK_1 = "Java";
    public static final String VALID_STACK_2 = "JavaScript";
    public static final String VALID_STACK_3 = "Spring";
    public static final String VALID_STACK_4 = "React";

    public static PromptGenerationRequest getPromptGenerationRequest() {
        List<String> techStack = List.of(VALID_STACK_1, VALID_STACK_3);
        List<String> principles = List.of("OOP");

        return PromptGenerationRequest.builder()
                .promptType(VALID_PROMPT_TYPE_2)
                .featureDescription(VALID_FEATURE_DESCRIPTION_1)
                .exampleCode("예시 엔티티 클래스 코드")
                .techStack(techStack)
                .principles(principles)
                .responseFormat("Basic")
                .promptLanguage("English")
                .build();
    }

    public static PromptGenerationResponse getPromptGenerationResponse() {
        return new PromptGenerationResponse("생성된 프롬프트");
    }

    public static PromptSubmissionRequest getPromptSubmissionRequest() {
        return new PromptSubmissionRequest("입력된 프롬프트");
    }

    public static PromptSubmissionResponse getPromptSubmissionResponse() {
        return new PromptSubmissionResponse("ChatGPT API 응답");
    }

}
