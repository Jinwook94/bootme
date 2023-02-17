package com.bootme.auth.service;

import com.bootme.auth.dto.JwtVo;
import com.bootme.auth.exception.InvalidAudienceException;
import com.bootme.auth.exception.InvalidIssuedAtException;
import com.bootme.auth.exception.InvalidIssuerException;
import com.bootme.member.domain.Member;
import com.bootme.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Objects;

import static com.bootme.common.exception.ErrorType.*;


@Service
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;

    private final String GOOGLE = "google";
    private final String NAVER = "naver";
    private final String KAKAO = "kakao";
    private final String googleIss;
    private final String naverIss;
    private final String kakaoIss;
    private final String googleAud;
    private final String naverAud;
    private final String kakaoAud;

    public AuthService(MemberRepository memberRepository,
                       @Value("${security.jwt.google.issuer}") String googleIss,
                       @Value("${security.jwt.naver.issuer}") String naverIss,
                       @Value("${security.jwt.kakao.issuer}") String kakaoIss,
                       @Value("${security.jwt.google.audience}") String googleAud,
                       @Value("${security.jwt.naver.audience}") String naverAud,
                       @Value("${security.jwt.kakao.audience}") String kakaoAud) {
        this.memberRepository = memberRepository;
        this.googleIss = googleIss;
        this.naverIss = naverIss;
        this.kakaoIss = kakaoIss;
        this.googleAud = googleAud;
        this.naverAud = naverAud;
        this.kakaoAud = kakaoAud;
    }

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
        verifyAudience(body, issuer);
        verifyIssuedAt(body);

    }

    private String verifyIssuer(JwtVo.Body body){
        final String iss = body.getIss();

        if (Objects.equals(iss, googleIss)){
            return GOOGLE;
        } else if (Objects.equals(iss, naverIss)){
            return NAVER;
        } else if (Objects.equals(iss, kakaoIss)){
            return KAKAO;
        }
        throw new InvalidIssuerException(INVALID_ISSUER, iss);
    }

    private void verifyAudience(JwtVo.Body body, String issuer) {
        String expectedAud = null;
        switch (issuer) {
            case GOOGLE:
                expectedAud = googleAud;
                break;
            case NAVER:
                expectedAud = naverAud;
                break;
            case KAKAO:
                expectedAud = kakaoAud;
                break;
        }
        String actualAud = body.getAud();
        if (!Objects.equals(expectedAud, actualAud)) {
            throw new InvalidAudienceException(INVALID_AUDIENCE, actualAud);
        }
    }

    private void verifyIssuedAt(JwtVo.Body body){
        long iat = body.getIat();
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        long clockSkewTolerance = 300;

        if (iat > (now + clockSkewTolerance)) {
            throw new InvalidIssuedAtException(INVALID_ISSUED_AT, String.valueOf(iat));
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
