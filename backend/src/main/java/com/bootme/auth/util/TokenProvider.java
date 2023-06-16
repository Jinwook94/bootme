package com.bootme.auth.util;

import com.bootme.auth.dto.AuthInfo;
import com.bootme.auth.dto.AwsSecrets;
import com.bootme.member.domain.Member;
import com.bootme.member.service.MemberService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class TokenProvider {

    private final AwsSecrets awsSecrets;
    private final MemberService memberService;
    private final String domain;
    private final long accessTokenExpireTimeInMilliseconds;
    private final long refreshTokenExpireTimeInMilliseconds;

    public TokenProvider(AwsSecrets awsSecrets,
                         MemberService memberService,
                         @Value("${domain}") String domain,
                         @Value("${security.jwt.bootme.exp.millisecond.access}") long accessTokenExpireTimeInMilliseconds,
                         @Value("${security.jwt.bootme.exp.millisecond.refresh}") long refreshTokenExpireTimeInMilliseconds) {
        this.awsSecrets = awsSecrets;
        this.memberService = memberService;
        this.domain = domain;
        this.accessTokenExpireTimeInMilliseconds = accessTokenExpireTimeInMilliseconds;
        this.refreshTokenExpireTimeInMilliseconds = refreshTokenExpireTimeInMilliseconds;
    }

    public AuthInfo getAuthInfo(String accessToken) {
        Claims claims = parseToken(accessToken);
        Long id = claims.get("id", Long.class);
        Member member = memberService.findById(id);
        return new AuthInfo(id, member.getRoleType().toString(), member.getNickname());
    }

    public String getAccessTokenCookie(Long id, String email){
        String accessToken = createToken(id, email,accessTokenExpireTimeInMilliseconds);
        return getCookie("accessToken", accessToken, domain, accessTokenExpireTimeInMilliseconds);
    }

    public String getRefreshTokenCookie(Long id, String email){
        String refreshToken = createToken(id, email, refreshTokenExpireTimeInMilliseconds);
        return getCookie("refreshToken", refreshToken, domain, refreshTokenExpireTimeInMilliseconds);
    }

    private String createToken(Long id, String email, long expireTimeInMilliseconds) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expireTimeInMilliseconds);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("id", id)
                .claim("email", email)
                .setIssuer(awsSecrets.getBootmeIssuer())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    public String getCookie(String cookieName, String token, String domain, long expireTime) {
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
        String memberId = claims.get("id", String.class);
        String memberEmail = claims.get("email", String.class);
        return createToken(Long.parseLong(memberId), memberEmail, accessTokenExpireTimeInMilliseconds);
    }

    public Claims parseToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValid(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException e) {
            log.warn("JWT Exception: {}", e.getMessage());
            return false;
        }
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(awsSecrets.getBootmeSigningKey().getBytes(StandardCharsets.UTF_8));
    }

}