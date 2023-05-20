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
        private String kid;
        private String typ;
        private String alg;

        public Header() {
        }

        @Builder
        public Header(String kid, String typ, String alg) {
            this.kid = kid;
            this.typ = typ;
            this.alg = alg;
        }
    }

    @Getter
    public static class Body{
        private String oAuthProvider;
        private String iss;
        private String aud;
        private String sub;
        private Long iat;
        private Long exp;
        private String email;

        private String name;
        private String picture;
        private String given_name;
        private String family_name;
        private String azp;
        private String jti;
        private Long nbf;
        private boolean email_verified;
        private String ageRange;
        private String birthDay;
        private String birthYear;
        private String gender;
        private String id;
        private String phoneNumber;
        private String nickname;
        private String auth_time;

        public Body() {
        }

        @Builder
        public Body(String oAuthProvider, String iss, String aud, String sub, Long iat, Long exp, String email, String name, String picture, String given_name, String family_name, String azp, String jti, Long nbf, boolean email_verified, String ageRange, String birthDay, String birthYear, String gender, String id, String phoneNumber, String nickname, String auth_time) {
            this.oAuthProvider = oAuthProvider;
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

        public void setOAuthProvider(String oAuthProvider) {
            this.oAuthProvider = oAuthProvider;
        }
    }

}
