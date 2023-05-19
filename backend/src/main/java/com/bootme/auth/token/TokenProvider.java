package com.bootme.auth.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class TokenProvider {

    private final String issuer;
    private final Key signingKey;
    private final String domain;
    private final long accessTokenExpireTimeInMilliseconds;
    private final long refreshTokenExpireTimeInMilliseconds;

    public TokenProvider(@Value("${security.jwt.bootme.issuer}") String issuer,
                         @Value("${security.jwt.bootme.secret-key}") String secretKey,
                         @Value("${domain}") String domain,
                         @Value("${security.jwt.bootme.exp.millisecond.access}") long accessTokenExpireTimeInMilliseconds,
                         @Value("${security.jwt.bootme.exp.millisecond.refresh}") long refreshTokenExpireTimeInMilliseconds) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        this.issuer = issuer;
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        this.domain = domain;
        this.accessTokenExpireTimeInMilliseconds = accessTokenExpireTimeInMilliseconds;
        this.refreshTokenExpireTimeInMilliseconds = refreshTokenExpireTimeInMilliseconds;
    }

    public String getAccessTokenCookie(String id, String email){
        String accessToken = createToken(id, email,accessTokenExpireTimeInMilliseconds);
        return getCookie("accessToken", accessToken, domain, accessTokenExpireTimeInMilliseconds);
    }

    public String getRefreshTokenCookie(String id, String email){
        String refreshToken = createToken(id, email, refreshTokenExpireTimeInMilliseconds);
        return getCookie("refreshToken", refreshToken, domain, refreshTokenExpireTimeInMilliseconds);
    }

    private String createToken(String id, String email, long expireTimeInMilliseconds) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expireTimeInMilliseconds);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("id", id)
                .claim("email", email)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(signingKey)
                .compact();
    }

    private String getCookie(String cookieName, String token, String domain, long expireTime) {
        return ResponseCookie.from(cookieName, token)
                .sameSite("Lax")
                .domain(domain)
                .maxAge(expireTime/1000)
                .path("/")
                .secure(true)
                .httpOnly(true)
                .build()
                .toString();
    }

    public String reissueAccessToken(String refreshToken){
        Claims claims = parseToken(refreshToken);
        Long memberId = claims.get("id", Long.class);
        String memberEmail = claims.get("email", String.class);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenExpireTimeInMilliseconds);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("id", memberId)
                .claim("email", memberEmail)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(signingKey)
                .compact();
    }

    public Claims parseToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValid(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException e) {
            log.warn("JWT Exception: {}", e.getMessage());
            return false;
        }
    }

}