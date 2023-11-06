package com.bootme.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class AwsSecrets {

    private String apiUrl;
    private String googleClientId;
    private String googleIssuer;
    private String googleAudience;
    private String naverClientId;
    private String naverClientSecret;
    private String naverIssuer;
    private String naverAudience;
    private String naverSigningKey;
    private String kakaoRestApiKey;
    private String kakaoClientSecret;
    private String kakaoIssuer;
    private String kakaoAudience;
    private String kakaoJavascriptKey;
    private String bootmeIssuer;
    private String bootmeAudience;
    private String bootmeSigningKey;
    private String gaMeasurementId;

    public AwsSecrets() {
    }

    @Builder
    public AwsSecrets(String apiUrl, String googleClientId, String googleIssuer, String googleAudience, String naverClientId, String naverClientSecret,
                      String naverIssuer, String naverAudience, String naverSigningKey, String kakaoRestApiKey, String kakaoClientSecret,
                      String kakaoIssuer, String kakaoAudience, String kakaoJavascriptKey, String bootmeIssuer, String bootmeAudience, String bootmeSigningKey, String gaMeasurementId) {
        this.apiUrl = apiUrl;
        this.googleClientId = googleClientId;
        this.googleIssuer = googleIssuer;
        this.googleAudience = googleAudience;
        this.naverClientId = naverClientId;
        this.naverClientSecret = naverClientSecret;
        this.naverIssuer = naverIssuer;
        this.naverAudience = naverAudience;
        this.naverSigningKey = naverSigningKey;
        this.kakaoRestApiKey = kakaoRestApiKey;
        this.kakaoClientSecret = kakaoClientSecret;
        this.kakaoIssuer = kakaoIssuer;
        this.kakaoAudience = kakaoAudience;
        this.kakaoJavascriptKey = kakaoJavascriptKey;
        this.bootmeIssuer = bootmeIssuer;
        this.bootmeAudience = bootmeAudience;
        this.bootmeSigningKey = bootmeSigningKey;
        this.gaMeasurementId = gaMeasurementId;
    }

    public static AwsSecrets of(Map<String, String> secrets) {
        return AwsSecrets.builder()
                .apiUrl(secrets.get("api-url"))
                .googleClientId(secrets.get("google-client-id"))
                .googleIssuer(secrets.get("google-issuer"))
                .googleAudience(secrets.get("google-audience"))
                .naverClientId(secrets.get("naver-client-id"))
                .naverClientSecret(secrets.get("naver-client-secret"))
                .naverIssuer(secrets.get("naver-issuer"))
                .naverAudience(secrets.get("naver-audience"))
                .naverSigningKey(secrets.get("naver-signing-key"))
                .kakaoRestApiKey(secrets.get("kakao-rest-api-key"))
                .kakaoClientSecret(secrets.get("kakao-client-secret"))
                .kakaoIssuer(secrets.get("kakao-issuer"))
                .kakaoAudience(secrets.get("kakao-audience"))
                .kakaoJavascriptKey(secrets.get("kakao-javascript-key"))
                .bootmeIssuer(secrets.get("bootme-issuer"))
                .bootmeAudience(secrets.get("bootme-audience"))
                .bootmeSigningKey(secrets.get("bootme-signing-key"))
                .gaMeasurementId(secrets.get("ga-measurement-id"))
                .build();
    }

}
