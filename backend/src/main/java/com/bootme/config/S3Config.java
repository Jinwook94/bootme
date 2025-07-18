package com.bootme.config;

import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class S3Config {

    @Bean
    @Profile({"default", "dev"})
    public AmazonS3Client amazonS3Dev() {
        // 로컬 개발 환경에서는 AWS 프로필 사용
        return (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
    }

    @Bean
    @Profile({"staging", "prod"})
    public AmazonS3Client amazonS3Prod() {
        return (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withCredentials(InstanceProfileCredentialsProvider.getInstance())
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
    }
}
