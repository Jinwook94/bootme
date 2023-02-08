package com.bootme.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
    }

    @Getter
    @Setter
    public static class Body{
        private String OAuthProvider;
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
    }
}
