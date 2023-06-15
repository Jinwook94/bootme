package com.bootme.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.secretsmanager.caching.SecretCache;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.bootme.auth.dto.AwsSecrets;
import com.bootme.common.exception.SerializationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.bootme.common.exception.ErrorType.JSON_PROCESSING_FAIL;

@Configuration
public class AWSSecretsManagerConfig {

    @Bean
    public AWSSecretsManager awsSecretsManager() {
        return AWSSecretsManagerClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
    }

    @Bean
    public SecretCache secretCache(AWSSecretsManager awsSecretsManager) {
        return new SecretCache(awsSecretsManager);
    }

    @Bean
    public AwsSecrets awsSecrets(SecretCache secretCache) {
        String secretString = secretCache.getSecretString("/bootme/springboot");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> secretMap = new ConcurrentHashMap<>();
        try {
            secretMap = objectMapper.readValue(secretString, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new SerializationException(JSON_PROCESSING_FAIL, secretMap.toString(), e);
        }
        return AwsSecrets.of(secretMap);
    }

}
