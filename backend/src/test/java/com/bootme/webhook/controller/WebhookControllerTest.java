package com.bootme.webhook.controller;

import com.bootme.auth.service.AuthService;
import com.bootme.auth.token.TokenProvider;
import com.bootme.course.service.CourseService;
import com.bootme.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.bootme.util.fixture.WebhookFixture.getWebhookRequest;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(WebhookController.class)
@DisplayName("WebhookController 클래스의")
class WebhookControllerTest extends ControllerTest {

    @MockBean
    private AuthService authService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private TokenProvider tokenProvider;

    @Test
    @DisplayName("handleWebhook() 정상 요청시 상태코드 200을 반환한다.")
    void handleWebhook() throws Exception {
        // given
        String content = objectMapper.writeValueAsString(getWebhookRequest(1));
        willDoNothing().given(authService).verifyToken(anyString());
        willDoNothing().given(courseService).incrementClicks(anyLong());
        willDoNothing().given(courseService).incrementBookmarks(anyLong());

        // when
        ResultActions perform = mockMvc.perform(post("/webhook")
                .header("Bootme_Secret", "secret")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("webhook/handleWebhook/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

}