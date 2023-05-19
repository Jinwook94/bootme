package com.bootme.auth.controller;

import com.bootme.auth.service.AuthService;
import com.bootme.auth.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestHeader("Authorization") String authHeader) {
        String[] userInfo = authService.login(authHeader);
        String accessToken = tokenProvider.createAccessToken(userInfo[0], userInfo[1]);
        String refreshToken = tokenProvider.createRefreshToken(userInfo[0], userInfo[1]);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessToken)
                .header(HttpHeaders.SET_COOKIE, refreshToken)
                .body(userInfo[3]);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logOut(HttpServletResponse response) {
        response.addHeader(HttpHeaders.SET_COOKIE, "accessToken=; Max-Age=0;");
        response.addHeader(HttpHeaders.SET_COOKIE, "refreshToken=; Max-Age=0;");
        return ResponseEntity.noContent().build();
    }
}
