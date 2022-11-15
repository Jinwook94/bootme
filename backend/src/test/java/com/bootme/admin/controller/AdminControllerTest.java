package com.bootme.admin.controller;

import com.bootme.admin.dto.AdminLoginRequest;
import com.bootme.admin.service.AdminService;
import com.bootme.auth.dto.TokenResponse;
import com.bootme.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@WebMvcTest(AdminController.class)
@DisplayName("AdminController 클래스의")
class AdminControllerTest extends ControllerTest {

    @MockBean
    private AdminService adminService;

    @Nested
    @DisplayName("login 메서드는")
    class Login{

        private static final String BASE_SNIPPET_PATH = "admin/login/";

        @Test
        @DisplayName("정상 요청시 HTTP Status Code 200을 반환한다")
        void login() throws Exception {
            //given
            TokenResponse tokenResponse = new TokenResponse("tokenValue");
            given(adminService.login(any())).willReturn(tokenResponse);

            //when
            ResultActions perform = mockMvc.perform(post("/admin/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(new AdminLoginRequest("id", "password"))));

            //then
            perform.andExpect(status().isOk());

            //docs
            perform.andDo(print())
                    .andDo(document(BASE_SNIPPET_PATH + "success",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint())));
        }
    }
}