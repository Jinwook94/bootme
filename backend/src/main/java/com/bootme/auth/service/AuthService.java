package com.bootme.auth.service;

import com.bootme.auth.dto.JwtVo;
import com.bootme.auth.exception.InvalidIssuerException;
import com.bootme.member.domain.Member;
import com.bootme.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.Objects;

import static com.bootme.common.exception.ErrorType.Invalid_Issuer;


@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    private final String GOOGLE = "google";
    private final String NAVER = "naver";
    private final String KAKAO = "kakao";

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
        JwtVo.Body body = jwtVo.getBody();

        String issuer = verifyIssuer(body);

    }

    private String verifyIssuer(JwtVo.Body body){
        final String GOOGLE_ISS = "https://accounts.google.com";
        final String NAVER_ISS = "bootMe.frontend.naverLogin";
        final String KAKAO_ISS = "https://kauth.kakao.com";
        final String iss = body.getIss();

        if (Objects.equals(iss, GOOGLE_ISS)){
            return GOOGLE;
        } else if (Objects.equals(iss, NAVER_ISS)){
            return NAVER;
        } else if (Objects.equals(iss, KAKAO_ISS)){
            return KAKAO;
        }
        throw new InvalidIssuerException(Invalid_Issuer, iss);
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
        String issuer = verifyIssuer(jwtBody);
        jwtBody.setOAuthProvider(issuer);

        Member member = Member.of(jwtBody);
        memberRepository.save(member);
    }

    public String getUserInfo(JwtVo.Body jwtBody) {
        String nickname = jwtBody.getNickname();
        String name = jwtBody.getName();
        String idInEmail = jwtBody.getEmail().split("@")[0];

        if (nickname != null) {
            return "NickName=" + nickname + ", ProfileImage=" + jwtBody.getPicture();
        } else if (name != null) {
            return "NickName=" + name + ", ProfileImage=" + jwtBody.getPicture();
        } else
            return "NickName=" + idInEmail + ", ProfileImage=" + jwtBody.getPicture();
    }

}
