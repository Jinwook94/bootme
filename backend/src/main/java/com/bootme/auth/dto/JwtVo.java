package com.bootme.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtVo {

    private Header header;
    private Body body;


    @Getter
    public static class Header{
        private final String kid;
        private final String typ;
        private final String alg;

        @Builder
        public Header(String kid, String typ, String alg) {
            this.kid = kid;
            this.typ = typ;
            this.alg = alg;
        }
    }

    @Getter
    public static class Body{
        private String OAuthProvider;
        private final String iss;
        private final String aud;
        private final String sub;
        private final Long iat;
        private final Long exp;
        private final String email;

        private final String name;
        private final String picture;
        private final String given_name;
        private final String family_name;
        private final String azp;
        private final String jti;
        private final Long nbf;
        private final boolean email_verified;
        private final String ageRange;
        private final String birthDay;
        private final String birthYear;
        private final String gender;
        private final String id;
        private final String phoneNumber;
        private final String nickname;
        private final String auth_time;

        @Builder
        public Body(String OAuthProvider, String iss, String aud, String sub, Long iat, Long exp, String email, String name, String picture, String given_name, String family_name, String azp, String jti, Long nbf, boolean email_verified, String ageRange, String birthDay, String birthYear, String gender, String id, String phoneNumber, String nickname, String auth_time) {
            this.OAuthProvider = OAuthProvider;
            this.iss = iss;
            this.aud = aud;
            this.sub = sub;
            this.iat = iat;
            this.exp = exp;
            this.email = email;
            this.name = name;
            this.picture = picture;
            this.given_name = given_name;
            this.family_name = family_name;
            this.azp = azp;
            this.jti = jti;
            this.nbf = nbf;
            this.email_verified = email_verified;
            this.ageRange = ageRange;
            this.birthDay = birthDay;
            this.birthYear = birthYear;
            this.gender = gender;
            this.id = id;
            this.phoneNumber = phoneNumber;
            this.nickname = nickname;
            this.auth_time = auth_time;
        }

        public void setOAuthProvider(String OAuthProvider) {
            this.OAuthProvider = OAuthProvider;
        }
    }

}
