package com.bootme.auth.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class TokenProvider {

    private final String issuer;
    private final Key signingKey;
    private final long accessTokenExpireTimeInMilliseconds;
    private final long refreshTokenExpireTimeInMilliseconds;

    public TokenProvider(@Value("${security.jwt.bootme.issuer}") String issuer,
                         @Value("${security.jwt.bootme.secret-key}") String secretKey,
                         @Value("${security.jwt.bootme.exp.millisecond.access}") long accessTokenExpireTimeInMilliseconds,
                         @Value("${security.jwt.bootme.exp.millisecond.refresh}") long refreshTokenExpireTimeInMilliseconds) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        this.issuer = issuer;
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpireTimeInMilliseconds = accessTokenExpireTimeInMilliseconds;
        this.refreshTokenExpireTimeInMilliseconds = refreshTokenExpireTimeInMilliseconds;
    }

    public String createAccessToken(String id, String email) {
        return createToken(id, email, accessTokenExpireTimeInMilliseconds);
    }

    public String createRefreshToken(String id, String email) {
        return createToken(id, email, refreshTokenExpireTimeInMilliseconds);
    }

    private String createToken(String id, String email, long validityInMilliseconds) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityInMilliseconds);
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