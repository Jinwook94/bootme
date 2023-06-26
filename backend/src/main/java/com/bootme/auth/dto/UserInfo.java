package com.bootme.auth.dto;

public interface UserInfo {
    String getOAuthProvider();
    String getIss();
    String getEmail();
    String getName();
    String getNickname();
    String getPicture();
    void setOAuthProvider(String oAuthProvider);
}
