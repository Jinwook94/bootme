package com.bootme.config;

import com.bootme.common.exception.ValidationException;
import com.bootme.common.exception.ExternalServiceException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.S3Exception;

import static com.bootme.common.exception.ErrorType.MISSING_CONFIGURATION;
import static com.bootme.common.exception.ErrorType.EXTERNAL_SERVICE_EXCEPTION;

@Configuration
public class S3Config {

    private static final Logger logger = LoggerFactory.getLogger(S3Config.class);

    private final Environment environment;

    @Value("${app.aws.profile:}")
    private String awsProfile;

    @Value("${app.aws.region:ap-northeast-2}")
    private String awsRegion;

    @Value("${app.aws.s3.bucket-name}")
    private String bucketName;

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
        logger.info("Creating S3 client for production environment using ECS Task Role");

        // ECS Fargate에서는 ContainerCredentialsProvider 사용
        AwsCredentialsProvider credentialsProvider = ContainerCredentialsProvider.builder().build();

        S3Client s3Client = S3Client.builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.of(awsRegion))
                .build();

        // 자격 증명 및 버킷 접근 검증
        validateS3Access(s3Client, "production");

        return s3Client;
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

    /**
     * S3 자격 증명 및 버킷 접근 권한을 검증합니다.
     * 애플리케이션 시작 시점에 문제를 발견하여 fast-fail을 보장합니다.
     */
    private void validateS3Access(S3Client s3Client, String environment) {
        logger.info("Validating S3 access for {} environment. Bucket: {}", environment, bucketName);

        try {
            // HeadBucket 요청으로 버킷 존재 여부 및 접근 권한 확인
            HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            s3Client.headBucket(headBucketRequest);
            logger.info("Successfully validated S3 access. Bucket '{}' is accessible in {} environment",
                    bucketName, environment);

        } catch (NoSuchBucketException e) {
            logger.error("S3 bucket '{}' does not exist", bucketName);
            throw new ExternalServiceException(EXTERNAL_SERVICE_EXCEPTION,
                    String.format("S3 bucket '%s' does not exist", bucketName), e);

        } catch (S3Exception e) {
            // 권한 문제 또는 기타 S3 관련 에러
            logger.error("Failed to access S3 bucket '{}'. Error: {}", bucketName, e.getMessage());
            throw new ExternalServiceException(EXTERNAL_SERVICE_EXCEPTION,
                    String.format("Failed to access S3 bucket '%s': %s", bucketName, e.getMessage()), e);

        } catch (SdkClientException e) {
            // 자격 증명 문제
            logger.error("AWS credentials validation failed. Error: {}", e.getMessage());
            throw new ExternalServiceException(EXTERNAL_SERVICE_EXCEPTION,
                    "AWS credentials validation failed: " + e.getMessage(), e);
        }
    }
}
