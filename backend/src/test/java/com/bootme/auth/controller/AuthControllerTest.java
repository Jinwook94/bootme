package com.bootme.auth.controller;

import com.bootme.common.exception.TokenParseException;
import com.bootme.common.exception.AuthenticationException;
import com.bootme.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static com.bootme.common.exception.ErrorType.*;
import static com.bootme.util.fixture.AuthFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@DisplayName("AuthController 클래스의")
class AuthControllerTest extends ControllerTest {

    @Test
    @DisplayName("login()는 정상 요청시 상태코드 200을 반환한다.")
    void login_success() throws Exception {
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
    @DisplayName("login()는 JWT 파싱에 실패한 경우 400 Bad Request 를 반환한다.")
    void login_TOKEN_PARSING_FAIL() throws Exception {
        //given
        String invalidJwt = "InvalidBase64PayloadString.eyJhdWQiOiJkMGMwZmEzMWU1ZDQ4YmMzZjQ5MDIwZTExNjY4NDgwOSIsInN1YiI6IjI2NDY1ODY0NTYiLCJhdXRoX3RpbWUiOjE2ODQ1ODE2MzUsImlzcyI6Imh0dHBzOi8va2F1dGgua2FrYW8uY29tIiwibmlja25hbWUiOiLsp4TsmrEiLCJleHAiOjE2ODQ2MDMyMzUsImlhdCI6MTY4NDU4MTYzNSwicGljdHVyZSI6Imh0dHA6Ly9rLmtha2FvY2RuLm5ldC9kbi9kcGs5bDEvYnRxbUdoQTJsS0wvT3owd0R1Sm4xWVYyREluOTJmNkRWSy9pbWdfMTEweDExMC5qcGciLCJlbWFpbCI6ImdvZGZhdGhlcjk0QGtha2FvLmNvbSJ9.UjCa91vPFHFD7K58QFYvT1JJNcXNlCJ1tdVuhW3L4Yzt1klLEypYz-OwDLq_Ggviaf30ImWK5CWzCtOx19UnTmlipwfnGaPA7xhVdTaexsQsa4_tST25weUfrtbii0g6joFzivdYiycbAXKF_dHjjabZLWRZDow8z7t5zcOl1fZRJTfuWWrr-NzmQFcuWn0caFlWybp1s2wcOY9Y6AvyzHgvANyORL6KgkPby_U_tLsXRMtxe-K4-xw7AGkfk3WWV4DKHlotPBIa5zb6xKUXlO-5r4Or70qt13PM6KgHJ0bWUA_HPrt4hflSyemxZlAkFYtZd5IcIQ9btE-EcwbanA";
        given(authService.login(any()))
                .willThrow(new TokenParseException(TOKEN_PARSING_FAIL, invalidJwt));

        //when
        ResultActions perform = mockMvc.perform(post("/login")
                .header("Authorization", "Bearer " + invalidJwt)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpectAll(
                status().isBadRequest(),
                jsonPath("message").value(TOKEN_PARSING_FAIL.getMessage())
        );

        //docs
        perform.andDo(print())
                .andDo(document("auth/login/fail/token-parsing-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("login()는 토큰 발행자가 유효하지 않은 경우 401 Unauthorized 를 반환한다.")
    void login_INVALID_ISSUER() throws Exception {
        //given
        String invalidIssuerJwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJpbnZhbGlkSXNzdWVyIiwiaWF0IjoxNjg0NTgyMTA5LCJleHAiOjE3MTYxMTgxMDksImF1ZCI6Imh0dHBzOi8va2F1dGgua2FrYW8uY29tLyIsInN1YiI6IjI2NDY1ODY0NTYiLCJhdXRoX3RpbWUiOiIxNjg0NTgxNjM1Iiwibmlja25hbWUiOiLsp4TsmrEiLCJwaWN0dXJlIjoiaHR0cDovL2sua2FrYW9jZG4ubmV0L2RuL2RwazlsMS9idHFtR2hBMmxLTC9PejB3RHVKbjFZVjJESW45MmY2RFZLL2ltZ18xMTB4MTEwLmpwZyIsImVtYWlsIjoiZ29kZmF0aGVyOTRAa2FrYW8uY29tIn0.bNW6GWrscwfSA2oey_D_DniCvH2Dpr8rJVKuIckZVcM";
        given(authService.login(any()))
                .willThrow(new AuthenticationException(INVALID_ISSUER, invalidIssuerJwt));

        //when
        ResultActions perform = mockMvc.perform(post("/login")
                .header("Authorization", "Bearer " + invalidIssuerJwt)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpectAll(
                status().isUnauthorized(),
                jsonPath("message").value(INVALID_ISSUER.getMessage())
        );

        //docs
        perform.andDo(print())
                .andDo(document("auth/login/fail/invalid-issuer",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("login()는 토큰 Audience 가 유효하지 않은 경우 401 Unauthorized 를 반환한다.")
    void login_INVALID_AUDIENCE() throws Exception {
        //given
        String invalidAudJwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2Jvb3RtZS5mcm9udC5jb20iLCJpYXQiOjE2ODQ1ODIxMDksImV4cCI6MTcxNjExODEwOSwiYXVkIjoiaW52YWxpZEF1ZGllbmNlIiwic3ViIjoiMjY0NjU4NjQ1NiIsImF1dGhfdGltZSI6IjE2ODQ1ODE2MzUiLCJuaWNrbmFtZSI6IuynhOyasSIsInBpY3R1cmUiOiJodHRwOi8vay5rYWthb2Nkbi5uZXQvZG4vZHBrOWwxL2J0cW1HaEEybEtML096MHdEdUpuMVlWMkRJbjkyZjZEVksvaW1nXzExMHgxMTAuanBnIiwiZW1haWwiOiJnb2RmYXRoZXI5NEBrYWthby5jb20ifQ.bbzTjATw7WaD285BDqKJpWsAXa7JYbsNTGuFRWTc8R0";
        given(authService.login(any()))
                .willThrow(new AuthenticationException(INVALID_AUDIENCE, invalidAudJwt));

        //when
        ResultActions perform = mockMvc.perform(post("/login")
                .header("Authorization", "Bearer " + invalidAudJwt)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpectAll(
                status().isUnauthorized(),
                jsonPath("message").value(INVALID_AUDIENCE.getMessage())
        );

        //docs
        perform.andDo(print())
                .andDo(document("auth/login/fail/invalid-audience",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("login()는 토큰 발행 시간이 유효하지 않은 경우 401 Unauthorized 를 반환한다.")
    void login_INVALID_ISSUED_AT() throws Exception {
        //given
        Date now = new Date();
        Date issuedAt = new Date(now.getTime() + 600000); // 토큰 발행시간이 미래
        String invalidIatJwt = getJwt(issuedAt, VALID_EXP);
        given(authService.login(any()))
                .willThrow(new AuthenticationException(INVALID_ISSUED_AT, invalidIatJwt));

        //when
        ResultActions perform = mockMvc.perform(post("/login")
                .header("Authorization", "Bearer " + invalidIatJwt)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpectAll(
                status().isUnauthorized(),
                jsonPath("message").value(INVALID_ISSUED_AT.getMessage())
        );

        //docs
        perform.andDo(print())
                .andDo(document("auth/login/fail/invalid-issued-at",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("login()는 토큰이 만료된 경우 401 Unauthorized 를 반환한다.")
    void login_TOKEN_EXPIRED() throws Exception {
        //given
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() - 600000); // 토큰 만료시간이 과거
        String invalidExpJwt = getJwt(VALID_IAT, expirationTime);
        given(authService.login(any()))
                .willThrow(new AuthenticationException(TOKEN_EXPIRED, invalidExpJwt));

        //when
        ResultActions perform = mockMvc.perform(post("/login")
                .header("Authorization", "Bearer " + invalidExpJwt)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpectAll(
                status().isUnauthorized(),
                jsonPath("message").value(TOKEN_EXPIRED.getMessage())
        );

        //docs
        perform.andDo(print())
                .andDo(document("auth/login/fail/token-expired",
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