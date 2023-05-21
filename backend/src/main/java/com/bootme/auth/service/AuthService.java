package com.bootme.auth.service;

import com.auth0.jwk.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bootme.auth.dto.JwtVo;
import com.bootme.auth.exception.*;
import com.bootme.auth.token.JwkProviderSingleton;
import com.bootme.common.exception.AuthenticationException;
import com.bootme.member.domain.Member;
import com.bootme.member.repository.MemberRepository;
import com.bootme.member.service.MemberService;
import com.bootme.notification.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Objects;

import static com.bootme.auth.token.GoogleIdTokenVerifierSingleton.verifyGoogleIdToken;
import static com.bootme.common.exception.ErrorType.*;


@Service
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final NotificationService notificationService;

    private final String BOOTME = "bootme";
    private final String GOOGLE = "google";
    private final String NAVER = "naver";
    private final String KAKAO = "kakao";
    private final String BOOTME_ISSUER;
    private final String GOOGLE_ISSUER;
    private final String NAVER_ISSUER;
    private final String KAKAO_ISSUER;
    private final String BOOTME_AUDIENCE;
    private final String GOOGLE_AUDIENCE;
    private final String NAVER_AUDIENCE;
    private final String KAKAO_AUDIENCE;
    private final String BOOTME_SECRET;
    private final String NAVER_SECRET;

    public AuthService(MemberRepository memberRepository,
                       MemberService memberService,
                       NotificationService notificationService,
                       @Value("${security.jwt.bootme_front.issuer}") String BOOTME_ISSUER,
                       @Value("${security.jwt.google.issuer}") String GOOGLE_ISSUER,
                       @Value("${security.jwt.naver.issuer}") String NAVER_ISSUER,
                       @Value("${security.jwt.kakao.issuer}") String KAKAO_ISSUER,
                       @Value("${security.jwt.bootme_front.audience}") String BOOTME_AUDIENCE,
                       @Value("${security.jwt.google.audience}") String GOOGLE_AUDIENCE,
                       @Value("${security.jwt.naver.audience}") String NAVER_AUDIENCE,
                       @Value("${security.jwt.kakao.audience}") String KAKAO_AUDIENCE,
                       @Value("${security.jwt.bootme_front.secret-key}") String BOOTME_SECRET,
                       @Value("${security.jwt.naver.secret}") String NAVER_SECRET) {
        this.memberRepository = memberRepository;
        this.memberService = memberService;
        this.notificationService = notificationService;
        this.BOOTME_ISSUER = BOOTME_ISSUER;
        this.GOOGLE_ISSUER = GOOGLE_ISSUER;
        this.NAVER_ISSUER = NAVER_ISSUER;
        this.KAKAO_ISSUER = KAKAO_ISSUER;
        this.BOOTME_AUDIENCE = BOOTME_AUDIENCE;
        this.GOOGLE_AUDIENCE = GOOGLE_AUDIENCE;
        this.NAVER_AUDIENCE = NAVER_AUDIENCE;
        this.KAKAO_AUDIENCE = KAKAO_AUDIENCE;
        this.BOOTME_SECRET = BOOTME_SECRET;
        this.NAVER_SECRET = NAVER_SECRET;
    }

    public String[] login(String authHeader){
        String idToken = getToken(authHeader);
        JwtVo jwtVo = parseToken(idToken);
        JwtVo.Body jwtBody = jwtVo.getBody();

        verifyToken(idToken);
        registerMember(jwtBody);
        return getUserInfo(jwtBody);
    }

    public String getToken(String authHeader) {
        return authHeader.replace("Bearer ", "");
    }

    /**
     * JWT 스트링을 파싱하여 JwoVO 인스턴스에 복사한다.
     *
     * @return JWT 에 포함된 정보(헤더, 바디)를 복사한 JwtVo 인스턴스
     */
    public JwtVo parseToken(String token) {
        try {
            String[] jwtParts = token.split("\\.");
            ObjectMapper mapper = new ObjectMapper();
            JwtVo.Header header = mapper.readValue(Base64.getDecoder().decode(jwtParts[0]), JwtVo.Header.class);
            JwtVo.Body body = mapper.readValue(Base64.getDecoder().decode(jwtParts[1]), JwtVo.Body.class);
            return new JwtVo(header, body);
        } catch (IOException e) {
            throw new TokenParseException(TOKEN_PARSING_FAIL, token, e);
        }
    }

    /**
     * ID 토큰의 발급자, Audience, 발행 시간, 만료 시간, 서명을 검증한다.
     */
    public void verifyToken(String idToken) {
        JwtVo jwtVo = parseToken(idToken);
        JwtVo.Body body = jwtVo.getBody();

        String issuer = verifyIssuer(body);
        verifyAudience(body, issuer);
        verifyIssuedAt(body);
        verifyExpiration(body);
        verifySignature(idToken, issuer);
    }

    private String verifyIssuer(JwtVo.Body body) {
        final String iss = body.getIss();

        if (Objects.equals(iss, BOOTME_ISSUER)) {
            return BOOTME;
        } else if (Objects.equals(iss, GOOGLE_ISSUER)) {
            return GOOGLE;
        } else if (Objects.equals(iss, NAVER_ISSUER)) {
            return NAVER;
        } else if (Objects.equals(iss, KAKAO_ISSUER)) {
            return KAKAO;
        }
        throw new AuthenticationException(INVALID_ISSUER, iss);
    }

    private void verifyAudience(JwtVo.Body body, String issuer) {
        String expectedAud = null;
        switch (issuer) {
            case BOOTME:
                expectedAud = BOOTME_AUDIENCE;
                break;
            case GOOGLE:
                expectedAud = GOOGLE_AUDIENCE;
                break;
            case NAVER:
                expectedAud = NAVER_AUDIENCE;
                break;
            case KAKAO:
                expectedAud = KAKAO_AUDIENCE;
                break;
        }
        String actualAud = body.getAud();
        if (!Objects.equals(expectedAud, actualAud)) {
            throw new AuthenticationException(INVALID_AUDIENCE, actualAud);
        }
    }

    private void verifyIssuedAt(JwtVo.Body body) {
        long iat = body.getIat();
        long now = Instant.now().getEpochSecond();
        long clockSkewTolerance = 300;

        if (iat > (now + clockSkewTolerance)) {
            throw new AuthenticationException(INVALID_ISSUED_AT, String.valueOf(iat));
        }
    }

    private void verifyExpiration(JwtVo.Body body) {
        long exp = body.getExp();
        long now = Instant.now().getEpochSecond();
        long clockSkewTolerance = 300;

        if (exp < (now - clockSkewTolerance)) {
            throw new AuthenticationException(TOKEN_EXPIRED, String.valueOf(exp));
        }
    }

    private void verifySignature(String jwt, String issuer) {
        switch (issuer) {
            case BOOTME:
                verifyBootmeSignature(jwt, BOOTME_SECRET);
                break;
            case GOOGLE:
                verifyGoogleSignature(jwt);
                break;
            case NAVER:
                verifyNaverSignature(jwt, NAVER_SECRET);
                break;
            case KAKAO:
                verifyKakaoSignature(jwt);
                break;
        }
    }

    private void verifyBootmeSignature(String jwt, String bootmeSecret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(bootmeSecret.getBytes()))
                    .build()
                    .parseClaimsJws(jwt);

        } catch (JwtException e) {
            throw new AuthenticationException(INVALID_SIGNATURE);
        }
    }

    private void verifyGoogleSignature(String idToken) {
        // Signature 정상이면 ID Token 반환, 비정상이면 null 반환함
        GoogleIdToken returnedIdToken = verifyGoogleIdToken(GOOGLE_AUDIENCE, idToken);

        if (returnedIdToken == null) {
            throw new AuthenticationException(INVALID_SIGNATURE);
        }
    }

    private void verifyNaverSignature(String jwt, String naverSecret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(naverSecret.getBytes()))
                    .build()
                    .parseClaimsJws(jwt);

        } catch (JwtException e) {
            throw new AuthenticationException(INVALID_SIGNATURE);
        }
    }

    private void verifyKakaoSignature(String idToken) {
        try {
            // 1. 서명 검증없이 디코딩
            DecodedJWT jwtOrigin = JWT.decode(idToken);

            // 2. 공개키 프로바이더 준비 (싱글톤)
            JwkProvider provider = JwkProviderSingleton.getInstance();

            Jwk jwk = provider.get(jwtOrigin.getKeyId());

            // 3. 서명 검증
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(idToken);

        } catch (SignatureVerificationException | JwkException e) {
            throw new AuthenticationException(INVALID_SIGNATURE);
        }
    }

    /**
     * 가입된 유저는 방문 횟수를 증가, 가입되지 않은 유저는 가입
     */
    public void registerMember(JwtVo.Body jwtBody) {
        boolean isRegistered = memberService.isMemberRegistered(jwtBody.getEmail());

        if (isRegistered) {
            incrementVisitsCount(jwtBody);
        } else {
            String issuer = verifyIssuer(jwtBody);
            jwtBody.setOAuthProvider(issuer);

            Member member = Member.of(jwtBody);
            Member savedMember = memberRepository.save(member);
            notificationService.sendNotification(savedMember, "signUp");
        }
    }

    private void incrementVisitsCount(JwtVo.Body jwtBody) {
        memberRepository.incrementVisits(jwtBody.getEmail());
    }

    // 반환값: id, email, userInfo(프론트엔드의 헤더 UI 등에 사용될 유저 정보)
    public String[] getUserInfo(JwtVo.Body jwtBody) {
        Long memberId = memberService.findByEmail(jwtBody.getEmail()).getId();
        String nickname = jwtBody.getNickname();
        String name = jwtBody.getName();
        String email = jwtBody.getEmail();
        String idInEmail = email.split("@")[0];

        String userInfo;
        if (nickname != null) {
            userInfo = "MemberId=" + memberId + ", NickName=" + nickname + ", ProfileImage=" + jwtBody.getPicture();
        } else if (name != null) {
            userInfo = "MemberId=" + memberId + ", NickName=" + name + ", ProfileImage=" + jwtBody.getPicture();
        } else
            userInfo ="MemberId=" + memberId + ", NickName=" + idInEmail + ", ProfileImage=" + jwtBody.getPicture();

        return new String[]{String.valueOf(memberId), email, userInfo};
    }

}
