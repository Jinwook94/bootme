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
import com.bootme.auth.token.TokenProvider;
import com.bootme.member.domain.Member;
import com.bootme.member.repository.MemberRepository;
import com.bootme.member.service.MemberService;
import com.bootme.notification.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Objects;

import static com.bootme.common.exception.ErrorType.*;


@Service
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private final NotificationService notificationService;

    private final String COOKIE_DOMAIN;
    private final long ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS;
    private final long REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS;
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
                       TokenProvider tokenProvider,
                       NotificationService notificationService,
                       @Value("${domain}") String COOKIE_DOMAIN,
                       @Value("${security.jwt.bootme.exp.millisecond.access}") long ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS,
                       @Value("${security.jwt.bootme.exp.millisecond.refresh}") long REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS,
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
        this.tokenProvider = tokenProvider;
        this.notificationService = notificationService;
        this.COOKIE_DOMAIN = COOKIE_DOMAIN;
        this.ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS = ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS;
        this.REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS = REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS;
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
        throw new InvalidIssuerException(INVALID_ISSUER, iss);
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
            throw new InvalidAudienceException(INVALID_AUDIENCE, actualAud);
        }
    }

    private void verifyIssuedAt(JwtVo.Body body) {
        long iat = body.getIat();
        long now = Instant.now().getEpochSecond();
        long clockSkewTolerance = 300;

        if (iat > (now + clockSkewTolerance)) {
            throw new InvalidIssuedAtException(INVALID_ISSUED_AT, String.valueOf(iat));
        }
    }

    private void verifyExpiration(JwtVo.Body body) {
        long exp = body.getExp();
        long now = Instant.now().getEpochSecond();
        long clockSkewTolerance = 300;

        if (exp < (now - clockSkewTolerance)) {
            throw new TokenExpiredException(TOKEN_EXPIRED, String.valueOf(exp));
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
            throw new InvalidSignatureException(INVALID_SIGNATURE, "verifyBootmeSignature()");
        }
    }

    private void verifyGoogleSignature(String idToken) {
        // Signature 정상이면 ID Token 반환, 비정상이면 null 반환함
//        GoogleIdToken returnedIdToken = verifyGoogleIdToken(GOOGLE_AUDIENCE, idToken);
//
//        if (returnedIdToken == null) {
//            throw new InvalidSignatureException(INVALID_SIGNATURE, "verifyGoogleSignature()");
//        }
    }

    private void verifyNaverSignature(String jwt, String naverSecret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(naverSecret.getBytes()))
                    .build()
                    .parseClaimsJws(jwt);

        } catch (JwtException e) {
            throw new InvalidSignatureException(INVALID_SIGNATURE, "verifyNaverSignature()");
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
            throw new InvalidSignatureException(INVALID_SIGNATURE, "verifyKakaoSignature()");
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

    public String[] createTokenCookies(JwtVo.Body jwtBody) {
        String accessToken = tokenProvider.createAccessToken(jwtBody);
        String refreshToken = tokenProvider.createRefreshToken(jwtBody);
        String accessTokenCookie = getCookie("accessToken", accessToken, COOKIE_DOMAIN, ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS/1000);
        String refreshTokenCookie = getCookie("refreshToken", refreshToken, COOKIE_DOMAIN, REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS/1000);
        return new String[]{accessTokenCookie, refreshTokenCookie};
    }

    private String getCookie(String tokenName, String token, String domain, long expireTime) {
        return ResponseCookie.from(tokenName, token)
                .sameSite("Lax")
                .domain(domain)
                .maxAge(expireTime)
                .path("/")
                .secure(true)
                .httpOnly(true)
                .build()
                .toString();
    }

    /**
     * 프론트엔드의 헤더와 메뉴 모달, 유저 드롭다운 컴포넌트에 사용될 유저정보를 전달
     */
    public String getUserInfo(JwtVo.Body jwtBody) {
        Long memberId = memberService.findByEmail(jwtBody.getEmail()).getId();
        String nickname = jwtBody.getNickname();
        String name = jwtBody.getName();
        String idInEmail = jwtBody.getEmail().split("@")[0];

        if (nickname != null) {
            return "MemberId=" + memberId + ", NickName=" + nickname + ", ProfileImage=" + jwtBody.getPicture();
        } else if (name != null) {
            return "MemberId=" + memberId + ", NickName=" + name + ", ProfileImage=" + jwtBody.getPicture();
        } else
            return "MemberId=" + memberId + ", NickName=" + idInEmail + ", ProfileImage=" + jwtBody.getPicture();
    }

}
