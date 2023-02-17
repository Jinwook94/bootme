package com.bootme.util.fixture;

import com.bootme.auth.dto.JwtVo;

public class AuthFixture {
    public static final JwtVo.Header VALID_JWT_HEADER = JwtVo.Header.builder()
                                                            .alg("RS256")
                                                            .kid("274052a2b6448745764e72c359091d90cfb585a")
                                                            .typ("JWT")
                                                            .build();
    public static final Long VALID_IAT = 1675313707L;
    public static final Long VALID_EXP = 1675313707L;
    public static final String VALID_EMAIL = "valid@email.com";

    public static JwtVo getJwtVo(String issuer, String audience){
        JwtVo.Body body = JwtVo.Body.builder()
                .iss(issuer)
                .aud(audience)
                .iat(VALID_IAT)
                .exp(VALID_EXP)
                .email(VALID_EMAIL)
                .build();
        return new JwtVo(VALID_JWT_HEADER, body);
    }

    public static JwtVo getJwtVo(String issuer, String audience, Long issuedAt){
        JwtVo.Body body = JwtVo.Body.builder()
                .iss(issuer)
                .aud(audience)
                .iat(issuedAt)
                .exp(VALID_EXP)
                .email(VALID_EMAIL)
                .build();
        return new JwtVo(VALID_JWT_HEADER, body);
    }

}
