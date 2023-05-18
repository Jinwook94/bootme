package com.bootme.auth.token;

import com.bootme.auth.dto.JwtVo;
import com.bootme.member.domain.Member;
import com.bootme.member.repository.MemberRepository;
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
    private final long validityInMilliseconds;
    private final long refreshTokenValidityMilliseconds;
    private final MemberRepository memberRepository;

    public TokenProvider(@Value("${security.jwt.bootme.issuer}") String issuer,
                         @Value("${security.jwt.bootme.secret-key}") String secretKey,
                         @Value("${security.jwt.bootme.exp.millisecond.access}") long validityInMilliseconds,
                         @Value("${security.jwt.bootme.exp.millisecond.refresh}") long refreshTokenValidityMilliseconds,
                         MemberRepository memberRepository) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        this.issuer = issuer;
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        this.validityInMilliseconds = validityInMilliseconds;
        this.refreshTokenValidityMilliseconds = refreshTokenValidityMilliseconds;
        this.memberRepository = memberRepository;
    }

    public String createAccessToken(JwtVo.Body body) {
        Member member = memberRepository.findByEmail(body.getEmail()).orElseThrow();
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("id", member.getId())
                .claim("email", member.getEmail())
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(signingKey)
                .compact();
    }

    public String createRefreshToken(JwtVo.Body body) {
        Member member = memberRepository.findByEmail(body.getEmail()).orElseThrow();
        Date now = new Date();
        Date expiration = new Date(now.getTime() + refreshTokenValidityMilliseconds);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("id", member.getId())
                .claim("email", member.getEmail())
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(signingKey)
                .compact();
    }

    public String reissueAccessToken(String refreshToken){
        Long memberId = parseToken(refreshToken).get("id", Long.class);
        String memberEmail = parseToken(refreshToken).get("email", String.class);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityInMilliseconds);

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
        } catch (MalformedJwtException e) {
            log.error("Malformed JWT: {}", e.getMessage());
            return false;
        } catch (ExpiredJwtException e) {
            log.warn("Expired JWT: {}", e.getMessage());
            return false;
        } catch (JwtException e) {
            log.warn("JWT Exception: {}", e.getMessage());
            return false;
        }
    }

}