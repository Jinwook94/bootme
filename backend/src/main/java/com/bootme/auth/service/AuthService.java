package com.bootme.auth.service;

import com.auth0.jwk.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bootme.auth.dto.*;
import com.bootme.auth.dto.NaverResponse.NaverUserInfo;
import com.bootme.auth.util.JwkProviderSingleton;
import com.bootme.common.enums.OAuthProvider;
import com.bootme.common.exception.*;
import com.bootme.member.domain.Member;
import com.bootme.member.domain.OauthInfo;
import com.bootme.member.repository.MemberRepository;
import com.bootme.notification.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.*;

import static com.bootme.auth.util.GoogleIdTokenVerifierSingleton.verifyGoogleIdToken;
import static com.bootme.common.enums.OAuthProvider.*;
import static com.bootme.common.exception.ErrorType.*;
import static com.bootme.notification.domain.NotificationEventType.SIGN_UP;


@Service
@Transactional
public class AuthService {

    private final AwsSecrets awsSecrets;
    private final MemberRepository memberRepository;
    private final NotificationService notificationService;
    private final List<String> allowedOrigins;

    public AuthService(AwsSecrets awsSecrets,
                       MemberRepository memberRepository,
                       NotificationService notificationService,
                       @Value("${allowed-origins}") String allowedOriginsString) {
        this.awsSecrets = awsSecrets;
        this.memberRepository = memberRepository;
        this.notificationService = notificationService;
        this.allowedOrigins = Arrays.asList(allowedOriginsString.split(","));
    }

    public void validateLogin(AuthInfo authInfo) {
        if (authInfo.getMemberId() == null) {
            throw new AuthenticationException(NOT_LOGGED_IN);
        }
    }

    public boolean validateLogin(Long memberId) {
        return memberId != null;
    }

    public LoginResponse login(String authHeader){
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
        } catch (IOException | IllegalArgumentException e ) {
            throw new TokenParseException(TOKEN_PARSING_FAIL, token, e);
        }
    }

    /**
     * ID 토큰의 발급자, Audience, 발행 시간, 만료 시간, 서명을 검증한다.
     */
    public void verifyToken(String idToken) {
        JwtVo jwtVo = parseToken(idToken);
        JwtVo.Body body = jwtVo.getBody();

        String issuerString = verifyIssuer(body);
        OAuthProvider issuer = OAuthProvider.fromString(issuerString);

        verifyAudience(body, issuer);
        verifyIssuedAt(body);
        verifyExpiration(body);
        verifySignature(idToken, issuer);
    }

    private String verifyIssuer(UserInfo body) {
        final String iss = body.getIss();

        if (Objects.equals(iss, awsSecrets.getBootmeIssuer())) {
            return BOOTME.toString();
        } else if (Objects.equals(iss, awsSecrets.getGoogleIssuer())) {
            return GOOGLE.toString();
        } else if (Objects.equals(iss, awsSecrets.getNaverIssuer())) {
            return NAVER.toString();
        } else if (Objects.equals(iss, awsSecrets.getKakaoIssuer())) {
            return KAKAO.toString();
        }
        throw new AuthenticationException(INVALID_ISSUER, iss);
    }

    private void verifyAudience(JwtVo.Body body, OAuthProvider issuer) {
        String expectedAud = null;
        switch (issuer) {
            case BOOTME:
                expectedAud = awsSecrets.getBootmeAudience();
                break;
            case GOOGLE:
                expectedAud = awsSecrets.getGoogleAudience();
                break;
            case NAVER:
                expectedAud = awsSecrets.getNaverAudience();
                break;
            case KAKAO:
                expectedAud = awsSecrets.getKakaoAudience();
                break;
            default:
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

    private void verifySignature(String jwt, OAuthProvider issuer) {
        switch (issuer) {
            case BOOTME:
                verifyBootmeSignature(jwt, awsSecrets.getBootmeSigningKey());
                break;
            case GOOGLE:
                verifyGoogleSignature(jwt);
                break;
            case NAVER:
                verifyNaverSignature(jwt, awsSecrets.getNaverSigningKey());
                break;
            case KAKAO:
                verifyKakaoSignature(jwt);
                break;
            default:
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
        GoogleIdToken returnedIdToken = verifyGoogleIdToken(awsSecrets.getGoogleAudience(), idToken);

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
            JWTVerifier verifier = JWT.require(algorithm)
                    .acceptLeeway(5)
                    .build();
            verifier.verify(idToken);

        } catch (SignatureVerificationException | JwkException e) {
            throw new AuthenticationException(INVALID_SIGNATURE);
        }
    }

    /**
     * 가입된 유저는 방문 횟수를 증가, 가입되지 않은 유저는 가입
     */
    public void registerMember(UserInfo userInfo) {
        boolean isRegistered = memberRepository.existsMemberByEmail(userInfo.getEmail());

        if (isRegistered) {
            incrementVisitsCount(userInfo);
        } else {
            String issuer = verifyIssuer(userInfo);
            userInfo.setOAuthProvider(issuer);

            OauthInfo oauthInfo = OauthInfo.of(userInfo);
            Member member = Member.of(oauthInfo);
            Member savedMember = memberRepository.save(member);

            notificationService.sendNotification(savedMember, SIGN_UP);
        }
    }

    private void incrementVisitsCount(UserInfo jwtBody) {
        memberRepository.incrementVisits(jwtBody.getEmail());
    }

    public LoginResponse getUserInfo(UserInfo jwtBody) {
        Member member = memberRepository.findByEmail(jwtBody.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MEMBER, jwtBody.getEmail()));
        String name = member.getName();
        String email = member.getEmail();
        String idInEmail = email.split("@")[0];
        String profileImage = member.getProfileImage();
        String job = member.getJob();

        String nickname = member.getNickname();
        if (nickname == null) {
            if (name != null) {
                nickname = name;
            } else {
                nickname = idInEmail;
            }
        }

        return LoginResponse.builder()
                .memberId(member.getId())
                .email(email)
                .nickname(nickname)
                .profileImage(profileImage)
                .job(job)
                .build();
    }

    public boolean verifyExistingMember(String token){
        JwtVo jwtVo = parseToken(token);
        String email = jwtVo.getBody().getEmail();
        return memberRepository.existsMemberByEmail(email);
    }

    public void verifySecretRequest(String secret, String origin) {
        String token = getToken(secret);
        verifyToken(token);
        verifyOrigin(origin);
    }

    private void verifyOrigin(String origin) {
        if (origin == null) {
            throw new AccessDeniedException(FORBIDDEN_REQUEST);
        }
        if (!allowedOrigins.contains(origin)) {
            throw new AccessDeniedException(FORBIDDEN_REQUEST, "origin=" + origin);
        }
    }

    public AwsSecrets getAwsSecrets() {
        return awsSecrets;
    }

    public LoginResponse processNaverLogin(String naverOauthUrl) {
        String naverAccessToken = getNaverAccessToken(naverOauthUrl);
        NaverUserInfo userInfo = getNaverUserInfo(naverAccessToken);
        registerMember(userInfo);
        return getUserInfo(userInfo);
    }

    private String getNaverAccessToken(String naverOauthUrl) {
        WebClient webClient = WebClient.create(naverOauthUrl);
        return webClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("access_token"))
                .block();
    }

    private NaverUserInfo getNaverUserInfo(String naverAccessToken) {
        WebClient webClient = WebClient.create("https://openapi.naver.com/v1/nid/me");
        NaverResponse response = webClient.get()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + naverAccessToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(NaverResponse.class)
                .block();

        if (response == null) {
            throw new AuthenticationException(NAVER_LOGIN_FAIL, naverAccessToken);
        }
        return response.getResponse();
    }

}
