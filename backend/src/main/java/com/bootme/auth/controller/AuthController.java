package com.bootme.auth.controller;

import com.bootme.auth.dto.JwtVo;
import com.bootme.auth.service.AuthService;
import com.bootme.auth.token.TokenProvider;
import com.bootme.member.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    private final long accessTokenExpireTimeInSeconds;
    private final long refreshTokenExpireTimeInSeconds;

    public AuthController(AuthService authService,
                          MemberService memberService,
                          TokenProvider tokenProvider,
                          @Value("${security.jwt.exp.second.access}") long accessTokenExpireTimeInSeconds,
                          @Value("${security.jwt.exp.second.refresh}") long refreshTokenExpireTimeInSeconds) {
        this.authService = authService;
        this.memberService = memberService;
        this.tokenProvider = tokenProvider;
        this.accessTokenExpireTimeInSeconds = accessTokenExpireTimeInSeconds;
        this.refreshTokenExpireTimeInSeconds = refreshTokenExpireTimeInSeconds;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestHeader("Authorization") String authHeader) throws Exception {
        String idToken = authService.getIdToken(authHeader);
        JwtVo jwtVo = authService.copyTokenToVo(idToken);
        JwtVo.Body jwtBody = jwtVo.getBody();

        // todo: 검증 로직 추가
        authService.verifyToken(jwtVo);

        boolean isRegistered = memberService.isMemberRegistered(jwtBody.getEmail());
        if (isRegistered) {
            authService.updateLastLogin(jwtBody);
            authService.incrementVisitsCount(jwtBody);
        } else {
            authService.registerMember(jwtBody);
        }

        String accessToken = tokenProvider.createAccessToken(jwtBody);
        String refreshToken = tokenProvider.createRefreshToken(jwtBody);
        ResponseCookie accessTokenCookie = getCookie("accessToken", accessToken, accessTokenExpireTimeInSeconds);
        ResponseCookie refreshTokenCookie = getCookie("refreshToken", refreshToken, refreshTokenExpireTimeInSeconds);

        String nickName = authService.getNickNameForHeader(jwtBody);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(nickName);
    }

    private ResponseCookie getCookie(String tokenName, String token, long expireTime) {
        return ResponseCookie.from(tokenName, token)
                .sameSite("Lax")
                .domain("localhost")
                .maxAge(expireTime)
                .path("/")
                .secure(true)
                .httpOnly(true)
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logOut(HttpServletResponse response) {
        response.addHeader(HttpHeaders.SET_COOKIE, "accessToken=; Max-Age=0;");
        response.addHeader(HttpHeaders.SET_COOKIE, "refreshToken=; Max-Age=0;");
        return ResponseEntity.noContent().build();
    }
}
