package com.bootme.auth.service;

import static com.bootme.util.fixture.AuthFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import com.bootme.auth.dto.JwtVo;
import com.bootme.auth.exception.InvalidIssuerException;
import com.bootme.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@DisplayName("AuthService 클래스의")
class AuthServiceTest extends ServiceTest {

    @Autowired
    private AuthService authService;

    @Value("${security.jwt.google.issuer}")
    private String validGoogleIss;

    @Value("${security.jwt.naver.issuer}")
    private String validNaverIss;

    @Value("${security.jwt.kakao.issuer}")
    private String validKakaoIss;

    @Value("${security.jwt.google.audience}")
    private String validGoogleAud;

    @Value("${security.jwt.naver.audience}")
    private String validNaverAud;

    @Value("${security.jwt.kakao.audience}")
    private String validKakaoAud;

    @DisplayName("verifyIssuer()는 ID 토큰 발행자가 유효한 경우 예외를 발생시키지 않는다.")
    @Test
    void verifyIssuer_valid() {
        JwtVo jwtVoGoogle = getJwtVo_issuer(validGoogleIss);
        JwtVo jwtVoNaver = getJwtVo_issuer(validNaverIss);
        JwtVo jwtVoKakao = getJwtVo_issuer(validKakaoIss);

        assertAll(
                () -> assertDoesNotThrow(() -> authService.verifyToken(jwtVoGoogle)),
                () -> assertDoesNotThrow(() -> authService.verifyToken(jwtVoNaver)),
                () -> assertDoesNotThrow(() -> authService.verifyToken(jwtVoKakao))
        );
    }

    @DisplayName("verifyIssuer()는 ID 토큰의 발행자가 유효하지 않을 경우 예외를 발생시킨다.")
    @Test
    void verifyIssuer_invalid() {
        JwtVo jwtVo = getJwtVo_issuer("https://invalid.issuer.com");
        assertThrows(InvalidIssuerException.class, () -> authService.verifyToken(jwtVo));
    }

}