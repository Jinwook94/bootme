package com.bootme.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RateLimitConfig {

    private Map<String, Bucket> buckets;

    @PostConstruct
    public void init() {
        buckets = new HashMap<>();
    }

    public Bucket resolveBucket(String ip) {
        return buckets.computeIfAbsent(ip, this::newBucket);
    }

    private Bucket newBucket(String ip) {
        validateEmpty(ip);

        Refill refill = Refill.greedy(20, Duration.ofHours(1)); // 시간 당 20개 토큰 허용
        Bandwidth limit = Bandwidth.classic(20, refill).withInitialTokens(20);
        return Bucket.builder().addLimit(limit).build();
    }

    private void validateEmpty(String ip) {
        if (ip == null || ip.isEmpty()) {
            throw new IllegalArgumentException("IP address must not be null or empty.");
        }
    }

}
