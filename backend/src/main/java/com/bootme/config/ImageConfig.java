package com.bootme.config;

import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ImageConfig {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Bean
    @Profile("dev")
    public AmazonS3 amazonS3Dev() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
    }

    @Bean
    @Profile("prod")
    public AmazonS3 amazonS3Prod() {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(InstanceProfileCredentialsProvider.getInstance())
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
    }

    @Bean
    public Tika mimeTypeParser() {
        return new Tika();
    }
}
