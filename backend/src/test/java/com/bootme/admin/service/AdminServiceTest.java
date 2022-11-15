package com.bootme.admin.service;

import com.bootme.admin.dto.AdminLoginRequest;
import com.bootme.admin.exception.InvalidAdminException;
import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.dto.TokenResponse;
import com.bootme.member.domain.RoleType;
import com.bootme.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.Assertions.assertThat;



@DisplayName("AdminService 클래스의")
class AdminServiceTest extends ServiceTest {

    @Nested
    @DisplayName("Login 메서드는")
    class Login{

        @Test
        @DisplayName("올바른 관리자 계정 입력시 토큰이 포함된 객체를 반환한다.")
        public void loginSuccess_token() {
            //given
            AdminLoginRequest request = new AdminLoginRequest("test_id", "test_password");

            //when
            TokenResponse tokenResponse = adminService.login(request);

            String token = tokenResponse.getToken();

            //then
            assertThat(token).isNotNull();
        }

        @Test
        @DisplayName("올바른 관리자 계정 입력시 반환된 토큰의 페이로드에서 ADMIN 롤과 닉네임을 확인할 수 있다.")
        public void loginSuccess_payload(){
            //given
            AdminLoginRequest request = new AdminLoginRequest("test_id", "test_password");

            //when
            TokenResponse tokenResponse = adminService.login(request);

            String token = tokenResponse.getToken();
            AuthInfo payload = jwtTokenProvider.getPayload(token);
            //then
            assertAll(
                    () -> assertThat(payload.getRole()).isEqualTo(RoleType.ADMIN.getName()),
                    () -> assertThat(payload.getNickname()).isEqualTo("ADMIN")
            );
        }

        @Test
        @DisplayName("올바르지 않은 관리자 계정 입력시 InvalidAdminException 반환한다.")
        public void loginFail_InvalidAdminException(){
            //given
            AdminLoginRequest wrongId = new AdminLoginRequest("wrong_id", "test_password");
            AdminLoginRequest wrongPassword = new AdminLoginRequest("test_id", "wrong_password");
            AdminLoginRequest wrongIdPassword = new AdminLoginRequest("wrong_id", "wrong_password");

            //when & then
            assertAll(
                    () -> assertThatThrownBy(() -> adminService.login(wrongId))
                            .isExactlyInstanceOf(InvalidAdminException.class),
                    () -> assertThatThrownBy(() -> adminService.login(wrongPassword))
                            .isExactlyInstanceOf(InvalidAdminException.class),
                    () -> assertThatThrownBy(() -> adminService.login(wrongIdPassword))
                            .isExactlyInstanceOf(InvalidAdminException.class)
            );
        }
    }
}