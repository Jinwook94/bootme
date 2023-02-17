package com.bootme.util.fixture;

import com.bootme.auth.dto.JwtVo;
import org.springframework.beans.factory.annotation.Value;

public class AuthFixture {
    public static final JwtVo.Header VALID_JWT_HEADER = JwtVo.Header.builder()
                                                            .alg("RS256")
                                                            .kid("274052a2b6448745764e72c359091d90cfb585a")
                                                            .typ("JWT")
                                                            .build();

    @Value("${security.jwt.google.issuer}")
    private static String validGoogleIss;

    @Value("${security.jwt.naver.issuer}")
    private static String validNaverIss;

    @Value("${security.jwt.kakao.issuer}")
    private static String validKakaoIss;

    @Value("${security.jwt.google.audience}")
    private static String validGoogleAud;

    @Value("${security.jwt.naver.audience}")
    private static String validNaverAud;

    @Value("${security.jwt.kakao.audience}")
    private static String validKakaoAud;

    public static final Long VALID_IAT = 1675313707L;
    public static final Long VALID_EXP = 1675313707L;
    public static final String VALID_EMAIL = "valid@email.com";

    public static JwtVo getJwtVo_issuer(String invalidIssuer){
        JwtVo.Body body = JwtVo.Body.builder()
                .iss(invalidIssuer)
                .aud(validGoogleAud)
                .iat(VALID_IAT)
                .exp(VALID_EXP)
                .email(VALID_EMAIL)
                .build();
        return new JwtVo(VALID_JWT_HEADER, body);
    }

}
