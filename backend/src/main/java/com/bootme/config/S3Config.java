package com.bootme.config;

import com.bootme.common.exception.ValidationException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import static com.bootme.common.exception.ErrorType.MISSING_CONFIGURATION;

@Configuration
public class S3Config {

    private static final Logger logger = LoggerFactory.getLogger(S3Config.class);

    private final Environment environment;

    @Value("${app.aws.profile:}")
    private String awsProfile;

    @Value("${app.aws.region:ap-northeast-2}")
    private String awsRegion;

    // 생성자 주입
    public S3Config(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void validateConfiguration() {
        // Environment 객체를 사용하여 활성 프로파일 확인
        String[] activeProfiles = environment.getActiveProfiles();

        // 테스트 프로파일 확인
        if (activeProfiles.length > 0 && "test".equals(activeProfiles[0])) {
            logger.info("Skipping AWS S3 configuration validation in test environment");
            return;
        }

        // 개발 환경에서 설정 검증
        if (activeProfiles.length == 0 || "dev".equals(activeProfiles[0]) || "default".equals(activeProfiles[0])) {
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

    @Bean
    @Profile("test")
    public S3Client s3ClientTest() {
        logger.info("Creating mock S3 client for test environment");
        // 테스트용 Mock S3Client 반환
        return S3Client.builder()
                .region(Region.of(awsRegion))
                .build();
    }
}
