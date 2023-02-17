package com.bootme.auth.service;

import static com.bootme.util.fixture.AuthFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import com.bootme.auth.dto.JwtVo;
import com.bootme.auth.exception.InvalidAudienceException;
import com.bootme.auth.exception.InvalidIssuedAtException;
import com.bootme.auth.exception.InvalidIssuerException;
import com.bootme.auth.exception.TokenExpiredException;
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

    @DisplayName("verifyToken()은 ID 토큰이 유효한 경우 예외를 발생시키지 않는다.")
    @Test
    void verifyToken() {
        JwtVo jwtVoGoogle = getJwtVo(validGoogleIss, validGoogleAud);
        JwtVo jwtVoNaver = getJwtVo(validNaverIss, validNaverAud);
        JwtVo jwtVoKakao = getJwtVo(validKakaoIss, validKakaoAud);

        assertAll(
                () -> assertDoesNotThrow(() -> authService.verifyToken(jwtVoGoogle)),
                () -> assertDoesNotThrow(() -> authService.verifyToken(jwtVoNaver)),
                () -> assertDoesNotThrow(() -> authService.verifyToken(jwtVoKakao))
        );
    }

    @DisplayName("verifyIssuer()는 ID 토큰의 iss 값이 유효하지 않을 경우 예외를 발생시킨다.")
    @Test
    void verifyToken_verifyIssuer() {
        JwtVo jwtVo = getJwtVo("https://invalid.issuer.com", validGoogleAud);
        assertThrows(InvalidIssuerException.class, () -> authService.verifyToken(jwtVo));
    }

    @DisplayName("verifyAudience()는 ID 토큰의 aud 값이 유효하지 않을 경우 예외를 발생시킨다.")
    @Test
    void verifyToken_verifyAudience(){
        JwtVo jwtVo = getJwtVo(validGoogleIss, "invalidAudience");
        assertThrows(InvalidAudienceException.class, () -> authService.verifyToken(jwtVo));
    }

    @DisplayName("verifyIssuedAt()은 ID 토큰의 iat 값이 유효하지 않을 경우 예외를 발생시킨다.")
    @Test
    void verifyToken_verifyIssuedAt(){
        // 발행 시간이 미래인 토큰
        JwtVo jwtVo = getJwtVo(validGoogleIss, validGoogleAud, 2524608000L);
        assertThrows(InvalidIssuedAtException.class, () -> authService.verifyToken(jwtVo));
    }

    @DisplayName("verifyExpiration()은 ID 토큰의 exp 값이 유효하지 않을 경우 예외를 발생시킨다.")
    @Test
    void verifyToken_verifyExpiration(){
        // 만료 된 토큰 (exp: 23-02-17 00:00:00 GMT+0900)
        JwtVo jwtVo = getJwtVo_exp(validGoogleIss, validGoogleAud, 1676559600L);
        assertThrows(TokenExpiredException.class, () -> authService.verifyToken(jwtVo));
    }

}