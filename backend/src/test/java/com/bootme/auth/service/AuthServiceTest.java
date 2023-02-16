package com.bootme.auth.service;

import static com.bootme.util.fixture.AuthFixture.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bootme.auth.dto.JwtVo;
import com.bootme.auth.exception.InvalidIssuerException;
import com.bootme.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("AuthService 클래스의")
class AuthServiceTest extends ServiceTest {

    @Autowired
    private AuthService authService;

    @DisplayName("verifyIssuer()는 ID 토큰 발행자가 유효한 경우 예외를 발생시키지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = { VALID_ISS_GOOGLE, VALID_ISS_NAVER, VALID_ISS_KAKAO })
    void verifyIssuer_valid(String issuer){
        JwtVo jwtVo = getJwtVo_issuer(issuer);
        assertDoesNotThrow(() -> authService.verifyToken(jwtVo));
    }

    @DisplayName("verifyIssuer()는 ID 토큰의 발행자가 유효하지 않을 경우 예외를 발생시킨다.")
    @Test
    void verifyIssuer_invalid() {
        JwtVo jwtVo = getJwtVo_issuer("https://invalid.issuer.com");
        assertThrows(InvalidIssuerException.class, () -> authService.verifyToken(jwtVo));
    }

}