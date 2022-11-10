package com.bootme.auth.token;

import com.bootme.auth.dto.AuthInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("JwtTokenProvider 클래스의")
class JwtTokenProviderTest {

    @Autowired
    private TokenManager tokenManager;

    @Nested
    @DisplayName("createAccessToken 메서드는")
    class createAccessToken {

        @Test
        @DisplayName("엑세스 토큰을 반환한다.")
        void createAccessToken() {
            //when
            String token = tokenManager.createAccessToken(new AuthInfo(1L, "ADMIN", "test"));

            //then
            assertThat(token).isNotNull();
        }
    }

    @Nested
    @DisplayName("createRefreshToken 메서드는")
    class createRefreshToken {

        @Test
        @DisplayName("리프레시 토큰을 반환한다.")
        void createRefreshToken() {
            //when
            String token = tokenManager.createRefreshToken();

            //then
            assertThat(token).isNotNull();
        }
    }

    @Nested
    @DisplayName("getPayload 메서드는")
    class getPayload {

        @Test
        @DisplayName("토큰의 페이로드를 반환한다.")
        void getPayload(){
            //given
            String token = tokenManager.createAccessToken(new AuthInfo(1L, "ADMIN", "accessToken"));

            //when
            AuthInfo payload = tokenManager.getPayload(token);

            //then
            assertAll(
                    () -> assertThat(payload.getId()).isEqualTo(1L),
                    () -> assertThat(payload.getRole()).isEqualTo("ADMIN"),
                    () -> assertThat(payload.getNickname()).isEqualTo("accessToken")
            );
        }
    }
}