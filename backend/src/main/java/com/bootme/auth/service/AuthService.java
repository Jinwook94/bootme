package com.bootme.auth.service;

import com.bootme.auth.dto.JwtVo;
import com.bootme.member.domain.Member;
import com.bootme.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;


@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public String getIdToken(String authHeader){
        return authHeader.replace("Bearer ", "");
    }

    // JWT 스트링을 파싱하여 JwtVo 인스턴스에 복사하고 해당 인스턴스를 반환함
    public JwtVo copyTokenToVo(String token) throws Exception {
        String[] jwtParts = token.split("\\.");
        ObjectMapper mapper = new ObjectMapper();
        JwtVo.Header header = mapper.readValue(Base64.getDecoder().decode(jwtParts[0]), JwtVo.Header.class);
        JwtVo.Body body = mapper.readValue(Base64.getDecoder().decode(jwtParts[1]), JwtVo.Body.class);
        return new JwtVo(header, body);
    }

    public void verifyToken(JwtVo jwtVo){
    }

    private String getOAuthProvider(JwtVo.Body body){
        String OAuthProvider = "";
        if(Objects.equals(body.getIss(), "https://accounts.google.com")){
            OAuthProvider = "google";
        } else if (Objects.equals(body.getIss(), "bootMe.frontend.naverLogin")) {
            OAuthProvider = "naver";
        } else if (Objects.equals(body.getIss(), "https://kauth.kakao.com")) {
            OAuthProvider = "kakao";
        }
        return OAuthProvider;
    }

    // todo: 라스트 세션으로 수정해야할듯
    public void updateLastLogin(JwtVo.Body jwtBody) throws Exception {
        int rowsAffected = memberRepository.updateLastLoginTime(jwtBody.getEmail(), LocalDateTime.now());
        // todo: 예외처리
        if (rowsAffected != 1){
            throw new Exception();
        }
    }

    // todo: 세션 카운트로 수정해야할듯
    public void incrementVisitsCount(JwtVo.Body jwtBody) throws Exception {
        int rowsAffected = memberRepository.incrementVisits(jwtBody.getEmail());
        // todo: 예외처리
        if (rowsAffected != 1){
            throw new Exception();
        }
    }

    public void registerMember(JwtVo.Body jwtBody){
        String OAuthProvider = getOAuthProvider(jwtBody);
        jwtBody.setOAuthProvider(OAuthProvider);

        Member member = Member.of(jwtBody);
        memberRepository.save(member);
    }

}
