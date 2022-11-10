package com.bootme.auth.token;

import com.bootme.auth.dto.AuthInfo;
import io.jsonwebtoken.Claims;

public interface TokenManager {

    String createAccessToken(AuthInfo authInfo);

    String createRefreshToken();

    AuthInfo getPayload(String token);
}
