package com.bootme.auth.controller;

import com.bootme.auth.service.AuthService;
import com.bootme.auth.token.TokenProvider;
import com.bootme.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@DisplayName("AuthController 클래스의")
class AuthControllerTest extends ControllerTest {

    @MockBean
    private AuthService authService;

    @MockBean
    private TokenProvider tokenProvider;

    @Test
    @DisplayName("login()는 정상 요청시 상태코드 200을 반환한다.")
    void login() throws Exception {
        //given
        String accessTokenCookie = "accessToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c; Path=/; Domain=bootme.co.kr; Max-Age=3600; Expires=Fri, 18 May 2023 16:51:53 GMT; Secure; HttpOnly; SameSite=Lax";
        String refreshTokenCookie = "refreshToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c; Path=/; Domain=bootme.co.kr; Max-Age=2592000; Expires=Sun, 17 Jun 2023 15:51:53 GMT; Secure; HttpOnly; SameSite=Lax";
        String[] userInfo = {"1", "john@gmail.com", "MemberId=1, NickName=John, ProfileImage=imageUrl"};
        given(tokenProvider.getAccessTokenCookie(any(),any())).willReturn(accessTokenCookie);
        given(tokenProvider.getRefreshTokenCookie(any(),any())).willReturn(refreshTokenCookie);
        given(authService.login(any())).willReturn(userInfo);

        //when
        ResultActions perform = mockMvc.perform(post("/login")
                .header("Authorization", "Bearer validIdToken")
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("auth/login/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("logout()은 정상 요청시 상태코드 204를 반환한다.")
    void logout() throws Exception {
        //when
        ResultActions perform = mockMvc.perform(post("/logout"));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document("auth/logout/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
}