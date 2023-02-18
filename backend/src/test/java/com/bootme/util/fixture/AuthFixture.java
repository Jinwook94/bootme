package com.bootme.util.fixture;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class AuthFixture {
    public static final String VALID_NAVER_ISSUER = "https://bootme.front.com";
    public static final String VALID_NAVER_AUDIENCE = "bootme.com";
    public static final Date VALID_IAT = new Date(new Date().getTime());
    public static final Date VALID_EXP = new Date(new Date().getTime() + 86400000);
    private static final String SECRET_KEY = "secretKeyForTesting:KeysUsedWithHMAC-SHA-AlgorithmsMustHaveASize>=256bits";
    private static final Key SIGNING_KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    // 토큰의 issuer, audience 검증시 사용
    public static String getJwt(String issuer, String audience) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuer(issuer)
                .setAudience(audience)
                .setIssuedAt(VALID_IAT)
                .setExpiration(VALID_EXP)
                .signWith(SIGNING_KEY)
                .compact();
    }

    // 토큰의 발행시간, 만료시간 검증시 사용
    public static String getJwt(Date issuedAt, Date expirationTime) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuer(VALID_NAVER_ISSUER)
                .setAudience(VALID_NAVER_AUDIENCE)
                .setIssuedAt(issuedAt)
                .setExpiration(expirationTime)
                .signWith(SIGNING_KEY)
                .compact();
    }

}
