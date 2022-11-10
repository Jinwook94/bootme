package com.bootme.auth.token;

import com.bootme.auth.dto.AuthInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider implements TokenManager {

    private final Key signingKey;
    private final long validityInMilliseconds;
    private final long refreshTokenValidityMilliseconds;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") String secretKey,
                            @Value("${security.jwt.token.expire-length.access}") long validityInMilliseconds,
                            @Value("${security.jwt.token.expire-length.refresh}") long refreshTokenValidityMilliseconds) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        this.validityInMilliseconds = validityInMilliseconds;
        this.refreshTokenValidityMilliseconds = refreshTokenValidityMilliseconds;
    }

    @Override
    public String createAccessToken(AuthInfo authInfo) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .claim("id", authInfo.getId())
                .claim("role", authInfo.getRole())
                .claim("nickname", authInfo.getNickname())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(signingKey)
                .compact();
    }

    @Override
    public String createRefreshToken() {
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenValidityMilliseconds);

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(signingKey)
                .compact();
    }

    @Override
    public AuthInfo getPayload(String token) {
        Claims payload;
        try {
            payload = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException ex) {
            Long id = ex.getClaims().get("id", Long.class);
            String role = ex.getClaims().get("role", String.class);
            String nickname = ex.getClaims().get("nickname", String.class);
            return new AuthInfo(id, role, nickname);
        }

        Long id = payload.get("id", Long.class);
        String role = payload.get("role", String.class);
        String nickname = payload.get("nickname", String.class);
        return new AuthInfo(id, role, nickname);
    }
}