package com.bootme.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import static com.bootme.common.enums.JwtIssuer.BOOTME_ISSUER;

@Getter
@Setter
public class NaverResponse {
    @JsonProperty("result-code")
    private String resultCode;
    private String message;
    private NaverUserInfo response;

    @Getter
    @Setter
    public static class NaverUserInfo implements UserInfo {
        private String iss = BOOTME_ISSUER;
        private String id;
        private String nickname;
        @JsonProperty("profile_image")
        private String profileImage;
        private String age;
        private String gender;
        private String email;
        @JsonProperty("mobile_e164")
        private String mobileE164;
        private String mobile;
        private String name;
        private String birthday;
        private String birthyear;

        private NaverUserInfo() {
        }

        @Override
        public String getPicture() {
            return profileImage;
        }

        @Override
        public String getIss() {
            return iss;
        }
    }

}

