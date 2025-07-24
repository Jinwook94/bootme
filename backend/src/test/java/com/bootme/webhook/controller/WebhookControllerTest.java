package com.bootme.webhook.controller;

import com.bootme.util.ControllerTest;
import com.bootme.webhook.dto.WebhookRequest;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static com.bootme.common.exception.ErrorType.INVALID_EVENT;
import static com.bootme.util.fixture.WebhookFixture.getWebhookRequest;
import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(WebhookController.class)
@DisplayName("WebhookController 클래스의")
class WebhookControllerTest extends ControllerTest {

    @Test
    @DisplayName("handleWebhook()는 정상 요청시 상태코드 200을 반환한다.")
    void handleWebhook() throws Exception {
        // given
        String content = objectMapper.writeValueAsString(getWebhookRequest(1));
        willDoNothing().given(authService).verifyToken(anyString());

        // when
        ResultActions perform = mockMvc.perform(post("/webhook")
                .header("bootmesecrets", "secret")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("webhook/handleWebhook/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("웹훅 처리")
                                .description("수신된 웹훅을 처리한다.")
                                .requestHeaders(
                                        headerWithName("bootmesecrets").description("BootMe 발행 JWT")
                                )
                                .requestFields(
                                        fieldWithPath("sessionId").description("세션 ID"),
                                        fieldWithPath("memberId").description("회원 ID"),
                                        fieldWithPath("event").description("웹훅 이벤트 유형"),
                                        fieldWithPath("data.*").description("웹훅 이벤트 정보"),
                                        fieldWithPath("currentUrl").description("웹훅 이벤트 발생한 페이지 URL"),
                                        fieldWithPath("createdAt").description("웹훅 이벤트 발생 시간")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("handleWebhook()는 유효한 웹훅 이벤트가 아닌 경우 400 Bad Request 를 반환한다.")
    void handleWebhook_INVALID_EVENT() throws Exception {
        // given
        Map<String, String> data = new HashMap<>() {{ put("oAuthProvider", "naver");}};
        WebhookRequest webhookRequest = WebhookRequest.builder()
                                            .sessionId(1L)
                                            .memberId(1L)
                                            .event("login")
                                            .data(data)
                                            .currentUrl("https://bootme.co.kr/")
                                            .createdAt(1684335600L)
                                            .build();
        String content = objectMapper.writeValueAsString(webhookRequest);
        willDoNothing().given(authService).verifyToken(any());

        // when
        ResultActions perform = mockMvc.perform(post("/webhook")
                .header("bootmesecrets", "secret")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // then
        perform.andExpectAll(
                status().isBadRequest(),
                jsonPath("message").value(INVALID_EVENT.getMessage())
        );

        // docs
        perform.andDo(print())
                .andDo(document("webhook/handleWebhook/fail/invalid-event",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

}
