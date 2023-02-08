package com.bootme.auth.controller;

import com.bootme.auth.dto.JwtVo;
import com.bootme.auth.service.AuthService;
import com.bootme.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestHeader("Authorization") String authHeader) throws Exception {
        String idToken = authService.getIdToken(authHeader);
        JwtVo jwtVo = authService.copyTokenToVo(idToken);
        JwtVo.Body jwtBody = jwtVo.getBody();

        // todo: 검증 로직 추가
        authService.verifyToken(jwtVo);

        // todo: 회원 정보 업데이트한지 30일 이상 지났으면 다시 업데이트 하는 로직 추가
        // 등록된 유저인지 확인 -> 등록 안됐으면 모든 정보 저장, 가입 되어 있으면 LastLoginAt 만 업데이트
        boolean isRegistered = memberService.isMemberRegistered(jwtBody.getEmail());
        if (isRegistered) {
            authService.updateLastLogin(jwtBody);
            authService.incrementVisitsCount(jwtBody);
        } else {
            authService.registerMember(jwtBody);
        }

        // todo: Access, Refresh 토큰 발행 및 만료 관리
        // todo: 헤더로 토큰 등 인증 인가 정보 전송
        return ResponseEntity.ok().build();
    }

}
