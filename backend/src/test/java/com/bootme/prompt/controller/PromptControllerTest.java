package com.bootme.prompt.controller;

import com.bootme.util.ControllerTest;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import reactor.core.publisher.Mono;

import static com.bootme.util.fixture.PromptFixture.*;
import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PromptController.class)
@DisplayName("PromptController 클래스의")
class PromptControllerTest extends ControllerTest {

    @Test
    @DisplayName("generatePrompt()는 정상 요청시 상태코드 200을 반환한다.")
    void generatePrompt() throws Exception {
        //given
        given(promptService.generatePrompt(any(), any())).willReturn(Mono.just(getPromptGenerationResponse()));
        String content = objectMapper.writeValueAsString(getPromptGenerationRequest());

        //when
        ResultActions perform = mockMvc.perform(post("/prompts/generation/{type}", "feature")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("prompts/generate/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("프롬프트 생성")
                                .description("유저의 입력 값을 ChatGPT API에 전송하고 ChatGPT 입력에 최적화된 형태의 프롬프트를 반환한다.")
                                .pathParameters(
                                        parameterWithName("type").description("프롬프트 분류")
                                )
                                .requestFields(
                                        fieldWithPath("promptType").description("프롬프트 분류"),
                                        fieldWithPath("techStack").description("기술 스택"),
                                        fieldWithPath("principles").description("관점 & 원칙"),
                                        fieldWithPath("responseFormat").description("답변 형식"),
                                        fieldWithPath("promptLanguage").description("프롬프트 언어"),
                                        fieldWithPath("question").description("질문 내용").optional(),
                                        fieldWithPath("example").description("예시").optional(),
                                        fieldWithPath("featureDescription").description("기능 설명").optional(),
                                        fieldWithPath("exampleCode").description("예시 코드").optional(),
                                        fieldWithPath("apiDescription").description("API 설명").optional(),
                                        fieldWithPath("erd").description("ERD").optional(),
                                        fieldWithPath("entity").description("엔티티 or 모델 코드").optional()
                                )
                                // todo: 응답 타입 Mono 인 경우 응답 바디 있어도 없다고 뜨는 에러 있음
                                // 참고: https://github.com/spring-projects/spring-restdocs/issues/564#issuecomment-441003527
//                                .responseFields(
//                                        fieldWithPath("generatedPrompt").description("생성된 프롬프트")
//                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("submitPrompt()는 정상 요청시 상태코드 200을 반환한다.")
    void submitPrompt() throws Exception {
        //given
        given(promptService.submitPrompt(any())).willReturn(Mono.just(getPromptSubmissionResponse()));
        String content = objectMapper.writeValueAsString(getPromptSubmissionRequest());

        //when
        ResultActions perform = mockMvc.perform(post("/prompts/submission")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("prompts/submit/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("프롬프트 입력")
                                .description("`프롬프트 생성`을 통해 생성된 프롬프트를 ChatGPT API에 전송하고 응답값을 반환한다.")
                                .requestFields(
                                        fieldWithPath("prompt").description("프롬프트")
                                )
                                // todo: 응답 타입 Mono 인 경우 응답 바디 있어도 없다고 뜨는 에러 있음
                                // 참고: https://github.com/spring-projects/spring-restdocs/issues/564#issuecomment-441003527
//                                .responseFields(
//                                        fieldWithPath("generatedPrompt").description("생성된 프롬프트")
//                                )
                                .build())
                ));
    }

}
