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
        // allow 20 tokens per hour
        Refill refill = Refill.greedy(20, Duration.ofHours(1));
        Bandwidth limit = Bandwidth.classic(20, refill).withInitialTokens(20);
        return Bucket.builder().addLimit(limit).build();
    }

}
