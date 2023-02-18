package com.bootme.auth.controller;

import com.bootme.auth.dto.JwtVo;
import com.bootme.auth.service.AuthService;
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestHeader("Authorization") String authHeader) throws Exception {
        String idToken = authService.getIdToken(authHeader);
        JwtVo jwtVo = authService.parseToken(idToken);
        JwtVo.Body jwtBody = jwtVo.getBody();

        authService.verifyToken(idToken);
        authService.registerMember(jwtBody);
        String[] tokenCookies = authService.createTokenCookies(jwtBody);
        String userInfo = authService.getUserInfo(jwtBody);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, tokenCookies[0])
                .header(HttpHeaders.SET_COOKIE, tokenCookies[1])
                .body(userInfo);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logOut(HttpServletResponse response) {
        response.addHeader(HttpHeaders.SET_COOKIE, "accessToken=; Max-Age=0;");
        response.addHeader(HttpHeaders.SET_COOKIE, "refreshToken=; Max-Age=0;");
        return ResponseEntity.noContent().build();
    }
}
