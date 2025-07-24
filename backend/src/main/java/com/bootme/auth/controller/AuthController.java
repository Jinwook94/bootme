package com.bootme.auth.controller;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.dto.AwsSecrets;
import com.bootme.auth.dto.LoginResponse;
import com.bootme.auth.service.AuthService;
import com.bootme.auth.util.Login;
import com.bootme.auth.util.TokenProvider;
import com.bootme.sse.SseEmitterManager;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final SseEmitterManager sseEmitterManager;
    private final AwsSecrets awsSecrets;
    private final String domain;

    public AuthController(AuthService authService,
                          TokenProvider tokenProvider,
                          SseEmitterManager sseEmitterManager,
                          AwsSecrets awsSecrets,
                          @Value("${domain}") String domain) {
        this.authService = authService;
        this.tokenProvider = tokenProvider;
        this.sseEmitterManager = sseEmitterManager;
        this.awsSecrets = awsSecrets;
        this.domain = domain;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestHeader("Authorization") String authHeader) {
        LoginResponse response = authService.login(authHeader);
        Long memberId = response.getMemberId();
        String email = response.getEmail();

        String accessTokenCookie = tokenProvider.getAccessTokenCookie(memberId, email);
        String refreshTokenCookie = tokenProvider.getRefreshTokenCookie(memberId, email);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie)
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie)
                .body(response);
    }

    @PostMapping("/login/naver")
    public ResponseEntity<LoginResponse> naverLogin(@RequestBody Map<String, String> body) {
        LoginResponse response = authService.naverLogin(body.get("url"));
        Long memberId = response.getMemberId();
        String email = response.getEmail();

        String accessTokenCookie = tokenProvider.getAccessTokenCookie(memberId, email);
        String refreshTokenCookie = tokenProvider.getRefreshTokenCookie(memberId, email);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie)
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie)
                .body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logOut(@Login AuthInfo authInfo, HttpServletResponse response) {
        sseEmitterManager.remove(authInfo.getMemberId());

        response.addHeader(HttpHeaders.SET_COOKIE, "accessToken=; Max-Age=0; Path=/; HttpOnly; Secure; SameSite=Lax; Domain=" + domain + ";");
        response.addHeader(HttpHeaders.SET_COOKIE, "refreshToken=; Max-Age=0; Path=/; HttpOnly; Secure; SameSite=Lax; Domain=" + domain + ";");
        return ResponseEntity.noContent().build();
    }

//    @IPFilter
    @GetMapping("/secrets")
    public ResponseEntity<AwsSecrets> getSecrets(@RequestHeader(name = "Bootme-Secret") String secretHeader,
                                                 @RequestHeader(value = "Origin", required = false) String origin) {
        authService.verifySecretRequest(secretHeader, origin);
        return ResponseEntity.ok(awsSecrets);
    }

}
