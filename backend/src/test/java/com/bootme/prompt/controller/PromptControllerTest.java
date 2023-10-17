package com.bootme.prompt.controller;

import com.bootme.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import reactor.core.publisher.Mono;

import static com.bootme.util.fixture.PromptFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
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
                        preprocessResponse(prettyPrint())));
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
                        preprocessResponse(prettyPrint())));
    }

}
