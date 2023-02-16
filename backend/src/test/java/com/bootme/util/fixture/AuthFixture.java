package com.bootme.util.fixture;

import com.bootme.auth.dto.JwtVo;

public class AuthFixture {
    public static final JwtVo.Header VALID_JWT_HEADER = JwtVo.Header.builder()
                                                            .alg("RS256")
                                                            .kid("274052a2b6448745764e72c359091d90cfb585a")
                                                            .typ("JWT")
                                                            .build();
    public static final String VALID_ISS_GOOGLE = "https://accounts.google.com";
    public static final String VALID_ISS_NAVER = "bootMe.frontend.naverLogin";
    public static final String VALID_ISS_KAKAO = "https://kauth.kakao.com";
    public static final String VALID_AUD = "5652393-rvt6dh5p3rt3f.apps.googleusercontent.com";
    public static final String VALID_SUB = "10849419112";
    public static final Long VALID_IAT = 1675313707L;
    public static final Long VALID_EXP = 1675313707L;
    public static final String VALID_EMAIL = "valid@emai성l.com";

    public static JwtVo getJwtVo_issuer(String invalidIssuer){
        JwtVo.Body body = JwtVo.Body.builder()
                .iss(invalidIssuer)
                .aud(VALID_AUD)
                .sub(VALID_SUB)
                .iat(VALID_IAT)
                .exp(VALID_EXP)
                .email(VALID_EMAIL)
                .build();
        return new JwtVo(VALID_JWT_HEADER, body);
    }

}
