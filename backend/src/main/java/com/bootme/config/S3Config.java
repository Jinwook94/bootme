package com.bootme.config;

import com.bootme.common.exception.ValidationException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import static com.bootme.common.exception.ErrorType.MISSING_CONFIGURATION;

@Configuration
public class S3Config {

    private static final Logger logger = LoggerFactory.getLogger(S3Config.class);

    @Value("${app.aws.profile:}")
    private String awsProfile;

    @Value("${app.aws.region:ap-northeast-2}")
    private String awsRegion;

    @PostConstruct
    public void validateConfiguration() {
        // 개발 환경에서 설정 검증
        String activeProfile = System.getProperty("spring.profiles.active", "default");
        if ("dev".equals(activeProfile) || "default".equals(activeProfile)) {
            if (awsProfile == null || awsProfile.isEmpty()) {
                throw new ValidationException(MISSING_CONFIGURATION,
                        "app.aws.profile is required for dev environment. Please set it in application-dev.yml");
            }
            logger.info("AWS S3 configuration validated - Using profile: {}", awsProfile);
        }
    }

    @Bean
    @Profile({"default", "dev"})
    public S3Client s3ClientDev() {
        logger.info("Creating S3 client for dev environment with profile: {}", awsProfile);

        if (awsProfile == null || awsProfile.isEmpty()) {
            throw new ValidationException(MISSING_CONFIGURATION,
                    "app.aws.profile is required for dev environment");
        }

        // SSO 프로필을 지원하는 ProfileCredentialsProvider 사용
        AwsCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create(awsProfile);

        return S3Client.builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.of(awsRegion))
                .build();
    }

    @Bean
    @Profile({"staging", "prod"})
    public S3Client s3ClientProd() {
        logger.info("Creating S3 client for production environment");

        // ECS 에서는 Instance Profile 사용
        AwsCredentialsProvider credentialsProvider = InstanceProfileCredentialsProvider.create();

        return S3Client.builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.of(awsRegion))
                .build();
    }
}
