package com.bootme.util.fixture;

import com.bootme.auth.dto.JwtVo;
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

    public static JwtVo getJwtVo() {
        JwtVo.Header header = new JwtVo.Header("f22235dasdsdd5fa528fdd12fe", "JWT", "HS256");
        JwtVo.Body body = JwtVo.Body.builder()
                .iss("https://kauth.kakao.com")
                .aud("d0c0fa31e5dc3f49020e684809")
                .sub("2623458456")
                .iat(1684500869L)
                .exp(1684522469L)
                .email("jinwook@kakao.com")
                .picture("http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg")
                .nickname("john")
                .auth_time("1684500869")
                .build();
        return new JwtVo(header, body);
    }

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
