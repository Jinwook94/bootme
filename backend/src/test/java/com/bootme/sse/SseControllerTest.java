package com.bootme.sse;

import com.bootme.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.any;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SseController.class)
@DisplayName("SseController 클래스의")
class SseControllerTest extends ControllerTest {

    @Test
    @DisplayName("connect()는 정상 요청시 SSE 연결을 성공하고 상태코드 200을 반환한다.")
    void connect() throws Exception {
        //given
        willDoNothing().given(sseEmitterManager).add(any(), any());

        //when
        ResultActions perform = mockMvc.perform(get("/connect")
                .accept(MediaType.TEXT_EVENT_STREAM_VALUE));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("sse/connect/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

}
