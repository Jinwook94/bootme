package com.bootme.auth.token;

import com.bootme.auth.dto.JwtVo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("JwtTokenProvider 클래스의")
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider tokenProvider;

    // todo: 테스트코드 수정 필요
//    @Nested
//    @DisplayName("createAccessToken 메서드는")
//    class createAccessToken {
//
//        @Test
//        @DisplayName("엑세스 토큰을 반환한다.")
//        void createAccessToken() {
//            //when
//            String token = tokenProvider.createAccessToken(JwtVo.Body body);
//
//            //then
//            assertThat(token).isNotNull();
//        }
//    }
//
//    @Nested
//    @DisplayName("createRefreshToken 메서드는")
//    class createRefreshToken {
//
//        @Test
//        @DisplayName("리프레시 토큰을 반환한다.")
//        void createRefreshToken() {
//            //when
//            String token = tokenProvider.createRefreshToken();
//
//            //then
//            assertThat(token).isNotNull();
//        }
//    }

}