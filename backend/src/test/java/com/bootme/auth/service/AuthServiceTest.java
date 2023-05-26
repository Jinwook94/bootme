package com.bootme.auth.service;

import static com.bootme.util.fixture.AuthFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import com.bootme.common.exception.AuthenticationException;
import com.bootme.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

@DisplayName("AuthService 클래스의")
class AuthServiceTest extends ServiceTest {

    @DisplayName("verifyIssuer()는 ID 토큰의 iss 값이 유효하지 않을 경우 예외를 발생시킨다.")
    @Test
    void verifyIssuer() {
        String jwt = getJwt("https://invalid.issuer.com", VALID_NAVER_AUDIENCE);
        assertThrows(AuthenticationException.class, () -> authService.verifyToken(jwt));
    }

    @DisplayName("verifyAudience()는 ID 토큰의 aud 값이 유효하지 않을 경우 예외를 발생시킨다.")
    @Test
    void verifyAudience(){
        String jwt = getJwt(VALID_NAVER_ISSUER, "invalidAudience");
        assertThrows(AuthenticationException.class, () -> authService.verifyToken(jwt));
    }

    @DisplayName("verifyIssuedAt()은 ID 토큰의 iat 값이 유효하지 않을 경우 예외를 발생시킨다.")
    @Test
    void verifyIssuedAt(){
        Date now = new Date();
        Date issuedAt = new Date(now.getTime() + 600000); // 토큰 발행시간이 미래

        String jwt = getJwt(issuedAt, VALID_EXP);
        assertThrows(AuthenticationException.class, () -> authService.verifyToken(jwt));
    }

    @DisplayName("verifyExpiration()은 ID 토큰의 exp 값이 유효하지 않을 경우 예외를 발생시킨다.")
    @Test
    void verifyExpiration(){
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() - 600000); // 토큰 만료시간이 과거

        String jwt = getJwt(VALID_IAT, expirationTime);
        assertThrows(AuthenticationException.class, () -> authService.verifyToken(jwt));
    }

}