package com.bootme.auth.controller;

import com.bootme.auth.dto.AwsSecrets;
import com.bootme.auth.service.AuthService;
import com.bootme.auth.util.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final String domain;

    public AuthController(AuthService authService,
                          TokenProvider tokenProvider,
                          @Value("${domain}") String domain ) {
        this.authService = authService;
        this.tokenProvider = tokenProvider;
        this.domain = domain;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestHeader("Authorization") String authHeader) {
        String[] userInfo = authService.login(authHeader);
        String accessTokenCookie = tokenProvider.getAccessTokenCookie(userInfo[0], userInfo[1]);
        String refreshTokenCookie = tokenProvider.getRefreshTokenCookie(userInfo[0], userInfo[1]);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie)
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie)
                .body(userInfo[2]);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logOut(HttpServletResponse response) {
        response.addHeader(HttpHeaders.SET_COOKIE, "accessToken=; Max-Age=0; Domain=" + domain +";");
        response.addHeader(HttpHeaders.SET_COOKIE, "refreshToken=; Max-Age=0; Domain=" + domain + ";");
        return ResponseEntity.noContent().build();
    }

//    @IPFilter
    @GetMapping("/secrets")
    public ResponseEntity<AwsSecrets> getSecrets(@RequestHeader(name = "Bootme_Secret") String secret,
                                                 @RequestHeader(value = "Origin", required = false) String origin) {
        authService.verifySecretRequest(secret, origin);
        AwsSecrets awsSecrets = authService.getAwsSecrets();
        return ResponseEntity.ok(awsSecrets);
    }

}
