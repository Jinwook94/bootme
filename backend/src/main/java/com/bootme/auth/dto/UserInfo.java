package com.bootme.auth.dto;

public interface UserInfo {
    String getOAuthProvider();
    String getIss();
    String getEmail();
    String getName();
    String getNickname();
    String getPicture();
    String getBirthDay();
    String getBirthYear();
    String getAgeRange();
    String getGender();
    String getPhoneNumber();
    void setOAuthProvider(String oAuthProvider);
}
