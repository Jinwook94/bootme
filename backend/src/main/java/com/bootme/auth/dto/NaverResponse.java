package com.bootme.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

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
        private String oAuthProvider = "naver";
        private String iss = "https://bootme.co.kr";
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

        public NaverUserInfo() {
        }

        @Override
        public String getPicture() {
            return profileImage;
        }

        @Override
        public String getBirthDay() {
            return birthday;
        }

        @Override
        public String getBirthYear() {
            return birthyear;
        }

        @Override
        public String getAgeRange() {
            return null;
        }

        @Override
        public String getPhoneNumber() {
            return mobile;
        }

        @Override
        public String getIss() {
            return "https://bootme.co.kr";
        }

        @Override
        public void setOAuthProvider(String oAuthProvider) {
            this.oAuthProvider = "naver";
        }

    }

}

