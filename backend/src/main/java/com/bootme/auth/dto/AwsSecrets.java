package com.bootme.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class AwsSecrets {

    private String apiUrl;
    private String googleClientId;
    private String googleAudience;
    private String naverClientId;
    private String naverClientSecret;
    private String naverAudience;
    private String naverSigningKey;
    private String kakaoRestApiKey;
    private String kakaoClientSecret;
    private String kakaoAudience;
    private String kakaoJavascriptKey;
    private String bootmeAudience;
    private String bootmeSigningKey;
    private String gaMeasurementId;

    public AwsSecrets() {
    }

    @Builder
    public AwsSecrets(String apiUrl, String googleClientId, String googleAudience, String naverClientId, String naverClientSecret,
                      String naverAudience, String naverSigningKey, String kakaoRestApiKey, String kakaoClientSecret,
                      String kakaoAudience, String kakaoJavascriptKey, String bootmeAudience, String bootmeSigningKey, String gaMeasurementId) {
        this.apiUrl = apiUrl;
        this.googleClientId = googleClientId;
        this.googleAudience = googleAudience;
        this.naverClientId = naverClientId;
        this.naverClientSecret = naverClientSecret;
        this.naverAudience = naverAudience;
        this.naverSigningKey = naverSigningKey;
        this.kakaoRestApiKey = kakaoRestApiKey;
        this.kakaoClientSecret = kakaoClientSecret;
        this.kakaoAudience = kakaoAudience;
        this.kakaoJavascriptKey = kakaoJavascriptKey;
        this.bootmeAudience = bootmeAudience;
        this.bootmeSigningKey = bootmeSigningKey;
        this.gaMeasurementId = gaMeasurementId;
    }

    public static AwsSecrets of(Map<String, String> secrets) {
        return AwsSecrets.builder()
                .apiUrl(secrets.get("api-url"))
                .googleClientId(secrets.get("google-client-id"))
                .googleAudience(secrets.get("google-audience"))
                .naverClientId(secrets.get("naver-client-id"))
                .naverClientSecret(secrets.get("naver-client-secret"))
                .naverAudience(secrets.get("naver-audience"))
                .naverSigningKey(secrets.get("naver-signing-key"))
                .kakaoRestApiKey(secrets.get("kakao-rest-api-key"))
                .kakaoClientSecret(secrets.get("kakao-client-secret"))
                .kakaoAudience(secrets.get("kakao-audience"))
                .kakaoJavascriptKey(secrets.get("kakao-javascript-key"))
                .bootmeAudience(secrets.get("bootme-audience"))
                .bootmeSigningKey(secrets.get("bootme-signing-key"))
                .gaMeasurementId(secrets.get("ga-measurement-id"))
                .build();
    }

}
