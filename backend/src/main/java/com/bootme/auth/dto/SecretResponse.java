package com.bootme.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class SecretResponse {

    private String apiUrl;
    private String googleClientId;
    private String naverClientId;
    private String naverClientSecret;
    private String naverIssuer;
    private String naverAudience;
    private String naverSigningKey;
    private String kakaoRestApiKey;
    private String kakaoClientSecret;
    private String webhookIssuer;
    private String webhookAudience;
    private String webhookSigningKey;
    private String kakaoJavascriptKey;

    public SecretResponse() {
    }

    @Builder
    public SecretResponse(String apiUrl, String googleClientId, String naverClientId, String naverClientSecret,
                          String naverIssuer, String naverAudience, String naverSigningKey, String kakaoRestApiKey,
                          String kakaoClientSecret, String webhookIssuer, String webhookAudience, String webhookSigningKey,
                          String kakaoJavascriptKey) {
        this.apiUrl = apiUrl;
        this.googleClientId = googleClientId;
        this.naverClientId = naverClientId;
        this.naverClientSecret = naverClientSecret;
        this.naverIssuer = naverIssuer;
        this.naverAudience = naverAudience;
        this.naverSigningKey = naverSigningKey;
        this.kakaoRestApiKey = kakaoRestApiKey;
        this.kakaoClientSecret = kakaoClientSecret;
        this.webhookIssuer = webhookIssuer;
        this.webhookAudience = webhookAudience;
        this.webhookSigningKey = webhookSigningKey;
        this.kakaoJavascriptKey = kakaoJavascriptKey;
    }

    public static SecretResponse of(Map<String, String> secrets) {
        return SecretResponse.builder()
                .apiUrl(secrets.get("api-url"))
                .googleClientId(secrets.get("google-client-id"))
                .naverClientId(secrets.get("naver-client-id"))
                .naverClientSecret(secrets.get("naver-client-secret"))
                .naverIssuer(secrets.get("naver-issuer"))
                .naverAudience(secrets.get("naver-audience"))
                .naverSigningKey(secrets.get("naver-signing-key"))
                .kakaoRestApiKey(secrets.get("kakao-rest-api-key"))
                .kakaoClientSecret(secrets.get("kakao-client-secret"))
                .webhookIssuer(secrets.get("webhook-issuer"))
                .webhookAudience(secrets.get("webhook-audience"))
                .webhookSigningKey(secrets.get("webhook-signing-key"))
                .kakaoJavascriptKey(secrets.get("kakao-javascript-key"))
                .build();
    }

}
