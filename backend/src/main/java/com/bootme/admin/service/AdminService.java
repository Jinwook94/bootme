package com.bootme.admin.service;

import com.bootme.admin.domain.AdminAccount;
import com.bootme.admin.dto.AdminLoginRequest;
import com.bootme.admin.exception.InvalidAdminException;
import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.dto.TokenResponse;
import com.bootme.auth.token.JwtTokenProvider;
import com.bootme.common.exception.ErrorType;
import com.bootme.member.domain.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    public final JwtTokenProvider jwtTokenProvider;
    public final AdminAccount adminAccount;

    public TokenResponse login(AdminLoginRequest adminLoginRequest) {
        validateLogin(adminLoginRequest.getId(), adminLoginRequest.getPassword());
        AuthInfo authInfo = new AuthInfo(1L, RoleType.ADMIN.getName(), "ADMIN");
        String token = jwtTokenProvider.createAccessToken(authInfo);
        return new TokenResponse(token);
    }

    public void validateLogin(String id, String password) {
        if (!Objects.equals(adminAccount.getId(), id) || !Objects.equals(adminAccount.getPassword(), password)) {
            throw new InvalidAdminException(ErrorType.NOT_AUTHENTICATED);
        }
    }
}
