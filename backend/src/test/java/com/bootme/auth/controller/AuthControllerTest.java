package com.bootme.auth.controller;

import com.bootme.auth.dto.LoginResponse;
import com.bootme.common.exception.TokenParseException;
import com.bootme.common.exception.AuthenticationException;
import com.bootme.util.ControllerTest;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static com.bootme.common.exception.ErrorType.*;
import static com.bootme.util.fixture.AuthFixture.*;
import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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
        LoginResponse response = new LoginResponse(1L, "john@gmail.com", "John", "imageUrl", "Backend Developer");
        given(tokenProvider.getAccessTokenCookie(any(),any())).willReturn(accessTokenCookie);
        given(tokenProvider.getRefreshTokenCookie(any(),any())).willReturn(refreshTokenCookie);
        given(authService.login(any())).willReturn(response);

        //when
        ResultActions perform = mockMvc.perform(post("/auth/login")
                .header("Authorization", "Bearer validIdToken")
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("auth/login/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("구글, 카카오 로그인")
                                .description("구글, 카카오 로그인 (OpenID Connect) <br> 1. ID 토큰 검증 & 로그인 및 가입 처리 <br> 2. 쿠키에 엑세스 토큰과 리프레시 토큰을 반환, 바디에 유저 정보 반환")
                                .requestHeaders(
                                        headerWithName(AUTHORIZATION).description("ID Token")
                                )
                                .responseHeaders(
                                        headerWithName(SET_COOKIE).description("accessToken"),
                                        headerWithName(SET_COOKIE).description("refreshToken")
                                )
                                .responseFields(
                                        fieldWithPath("memberId").description("멤버 ID"),
                                        fieldWithPath("email").description("이메일"),
                                        fieldWithPath("nickname").description("닉네임"),
                                        fieldWithPath("profileImage").description("프로필 사진"),
                                        fieldWithPath("job").description("직업")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("login()는 JWT 파싱에 실패한 경우 400 Bad Request 를 반환한다.")
    void login_TOKEN_PARSING_FAIL() throws Exception {
        //given
        String invalidJwt = "InvalidBase64PayloadString.eyJhdWQiOiJkMGMwZmEzMWU1ZDQ4YmMzZjQ5MDIwZTExNjY4NDgwOSIsInN1YiI6IjI2NDY1ODY0NTYiLCJhdXRoX3RpbWUiOjE2ODQ1ODE2MzUsImlzcyI6Imh0dHBzOi8va2F1dGgua2FrYW8uY29tIiwibmlja25hbWUiOiLsp4TsmrEiLCJleHAiOjE2ODQ2MDMyMzUsImlhdCI6MTY4NDU4MTYzNSwicGljdHVyZSI6Imh0dHA6Ly9rLmtha2FvY2RuLm5ldC9kbi9kcGs5bDEvYnRxbUdoQTJsS0wvT3owd0R1Sm4xWVYyREluOTJmNkRWSy9pbWdfMTEweDExMC5qcGciLCJlbWFpbCI6ImdvZGZhdGhlcjk0QGtha2FvLmNvbSJ9.UjCa91vPFHFD7K58QFYvT1JJNcXNlCJ1tdVuhW3L4Yzt1klLEypYz-OwDLq_Ggviaf30ImWK5CWzCtOx19UnTmlipwfnGaPA7xhVdTaexsQsa4_tST25weUfrtbii0g6joFzivdYiycbAXKF_dHjjabZLWRZDow8z7t5zcOl1fZRJTfuWWrr-NzmQFcuWn0caFlWybp1s2wcOY9Y6AvyzHgvANyORL6KgkPby_U_tLsXRMtxe-K4-xw7AGkfk3WWV4DKHlotPBIa5zb6xKUXlO-5r4Or70qt13PM6KgHJ0bWUA_HPrt4hflSyemxZlAkFYtZd5IcIQ9btE-EcwbanA";
        given(authService.login(any()))
                .willThrow(new TokenParseException(TOKEN_PARSING_FAIL, invalidJwt));

        //when
        ResultActions perform = mockMvc.perform(post("/auth/login")
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
        ResultActions perform = mockMvc.perform(post("/auth/login")
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
        ResultActions perform = mockMvc.perform(post("/auth/login")
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
        ResultActions perform = mockMvc.perform(post("/auth/login")
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
        ResultActions perform = mockMvc.perform(post("/auth/login")
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
    @DisplayName("naverLogin()는 정상 요청시 상태코드 200을 반환한다.")
    void naverLogin_success() throws Exception {
        //given
        String accessTokenCookie = "accessToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c; Path=/; Domain=bootme.co.kr; Max-Age=3600; Expires=Fri, 18 May 2023 16:51:53 GMT; Secure; HttpOnly; SameSite=Lax";
        String refreshTokenCookie = "refreshToken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c; Path=/; Domain=bootme.co.kr; Max-Age=2592000; Expires=Sun, 17 Jun 2023 15:51:53 GMT; Secure; HttpOnly; SameSite=Lax";
        LoginResponse response = new LoginResponse(1L, "john@gmail.com", "John", "imageUrl", "Backend Developer");
        String content = "{\"url\":\"https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=ABCDEFG&client_secret=abcdefg&code=secretcode&state=secrretstate  \\n\"}";
        given(authService.naverLogin(any())).willReturn(response);
        given(tokenProvider.getAccessTokenCookie(any(),any())).willReturn(accessTokenCookie);
        given(tokenProvider.getRefreshTokenCookie(any(),any())).willReturn(refreshTokenCookie);

        //when
        ResultActions perform = mockMvc.perform(post("/auth/login/naver")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("auth/login/naver/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("네이버 로그인")
                                .description("네이버 로그인 (OAuth 2.0) <br> 1. Naver OAuth 서버로부터 엑세스 토큰 수신 <br> 2. 로그인 및 가입 처리 <br> 3. 쿠키에 엑세스 토큰과 리프레시 토큰을 반환, 바디에 유저 정보 반환")
                                .requestFields(
                                        fieldWithPath("url").description("네이버 OAuth URL")
                                )
                                .responseHeaders(
                                        headerWithName(SET_COOKIE).description("accessToken"),
                                        headerWithName(SET_COOKIE).description("refreshToken")
                                )
                                .responseFields(
                                        fieldWithPath("memberId").description("멤버 ID"),
                                        fieldWithPath("email").description("이메일"),
                                        fieldWithPath("nickname").description("닉네임"),
                                        fieldWithPath("profileImage").description("프로필 사진"),
                                        fieldWithPath("job").description("직")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("logout()은 정상 요청시 상태코드 204를 반환한다.")
    void logout() throws Exception {
        //when
        ResultActions perform = mockMvc.perform(post("/auth/logout"));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document("auth/logout/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("로그아웃")
                                .description("`Max-Age=0` 설정된 토큰 쿠키 응답을 통해 엑세스 토큰과 리프레시 토큰을 만료시켜 로그아웃 되도록 한다.")
                                .responseHeaders(
                                        headerWithName(SET_COOKIE).description("accessToken=; Max-Age=0;"),
                                        headerWithName(SET_COOKIE).description("refreshToken=; Max-Age=0;")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("getSecrets()는 정상 요청시 상태코드 200을 반환한다.")
    void getSecrets_success() throws Exception {
        //given
        String secret = "validSecret";
        String origin = "https://bootme.co.kr";
        willDoNothing().given(authService).verifySecretRequest(any(), any());

        //when
        ResultActions perform = mockMvc.perform(get("/auth/secrets")
                .header("Bootme-Secret", secret)
                .header("Origin", origin)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("auth/secrets/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("시크릿 요청")
                                .description("AWS Secrets Manager 저장된 시크릿 값 요청. (소셜 로그인 위한 API Key 등)")
                                .requestHeaders(
                                        headerWithName("Bootme-Secret").description("JWT 검증 목적"),
                                        headerWithName("Origin").description("허용된 origin 인지 검증 목적")
                                )
                                .responseFields(
                                        fieldWithPath("apiUrl").description("bootme 서버 URL"),
                                        fieldWithPath("googleClientId").description("googleClientId"),
                                        fieldWithPath("googleAudience").description("googleAudience"),
                                        fieldWithPath("naverClientId").description("naverClientId"),
                                        fieldWithPath("naverClientSecret").description("naverClientSecret"),
                                        fieldWithPath("naverAudience").description("naverAudience"),
                                        fieldWithPath("naverSigningKey").description("naverSigningKey"),
                                        fieldWithPath("kakaoRestApiKey").description("kakaoRestApiKey"),
                                        fieldWithPath("kakaoClientSecret").description("kakaoClientSecret"),
                                        fieldWithPath("kakaoAudience").description("kakaoAudience"),
                                        fieldWithPath("kakaoJavascriptKey").description("kakaoJavascriptKey"),
                                        fieldWithPath("bootmeAudience").description("bootmeAudience"),
                                        fieldWithPath("bootmeSigningKey").description("bootmeSigningKey"),
                                        fieldWithPath("gaMeasurementId").description("gaMeasurementId")
                                )
                                .build())
                ));
    }

}
