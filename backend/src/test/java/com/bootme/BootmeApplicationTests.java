package com.bootme;

import com.amazonaws.secretsmanager.caching.SecretCache;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.bootme.auth.dto.AwsSecrets;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class BootmeApplicationTests {

    @MockBean
    protected AWSSecretsManager awsSecretsManager;

    @MockBean
    protected SecretCache secretCache;

    @MockBean
    protected AwsSecrets awsSecrets;

    @Test
    void contextLoads() {
    }

}
