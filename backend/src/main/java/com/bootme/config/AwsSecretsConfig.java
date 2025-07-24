package com.bootme.config;

import com.bootme.auth.dto.AwsSecrets;
import com.bootme.common.exception.ExternalServiceException;
import com.bootme.common.exception.ValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

import java.util.Map;
import java.util.Properties;

import static com.bootme.common.exception.ErrorType.*;

@Configuration
public class AwsSecretsConfig {
    private static final Logger logger = LoggerFactory.getLogger(AwsSecretsConfig.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 환경 변수 상수
    private static final String AWS_SECRETS_MANAGER_SECRETS_ENV_VAR = "AWS_SECRETS_MANAGER_SECRETS";

    // AWS Secrets Manager 키 상수 - DB 관련
    private static final String SECRET_KEY_DB_URL = "backend_db_url";
    private static final String SECRET_KEY_OPENAI_API_KEY = "backend_openai_api_key";

    // AWS Secrets Manager 키 상수 - OAuth 및 기타
    private static final String SECRET_KEY_API_URL = "backend_api_url";
    private static final String SECRET_KEY_GOOGLE_CLIENT_ID = "backend_google_client_id";
    private static final String SECRET_KEY_GOOGLE_AUDIENCE = "backend_google_audience";
    private static final String SECRET_KEY_NAVER_CLIENT_ID = "backend_naver_client_id";
    private static final String SECRET_KEY_NAVER_CLIENT_SECRET = "backend_naver_client_secret";
    private static final String SECRET_KEY_NAVER_AUDIENCE = "backend_naver_audience";
    private static final String SECRET_KEY_NAVER_SIGNING_KEY = "backend_naver_signing_key";
    private static final String SECRET_KEY_KAKAO_REST_API_KEY = "backend_kakao_rest_api_key";
    private static final String SECRET_KEY_KAKAO_CLIENT_SECRET = "backend_kakao_client_secret";
    private static final String SECRET_KEY_KAKAO_AUDIENCE = "backend_kakao_audience";
    private static final String SECRET_KEY_KAKAO_JAVASCRIPT_KEY = "backend_kakao_javascript_key";
    private static final String SECRET_KEY_BOOTME_AUDIENCE = "backend_bootme_audience";
    private static final String SECRET_KEY_BOOTME_SIGNING_KEY = "backend_bootme_signing_key";
    private static final String SECRET_KEY_GA_MEASUREMENT_ID = "backend_ga_measurement_id";

    // Spring 프로퍼티 키 상수
    private static final String SPRING_DATASOURCE_URL = "spring.datasource.url";
    private static final String SPRING_DATASOURCE_USERNAME = "spring.datasource.username";
    private static final String SPRING_DATASOURCE_PASSWORD = "spring.datasource.password";
    private static final String OPENAI_API_KEY_PROPERTY = "openai.api-key";

    // 애플리케이션 설정 키 상수
    private static final String APP_AWS_SECRETS_MANAGER_SECRET_NAME = "app.aws.secrets-manager.secret-name";
    private static final String APP_AWS_PROFILE = "app.aws.profile";
    private static final String APP_AWS_REGION = "app.aws.region";

    // 기타 상수
    private static final String DEFAULT_REGION = "ap-northeast-2";
    private static final String DEV_PROFILE = "dev";
    private static final String PROPERTY_SOURCE_NAME = "awsSecrets";

    // AwsSecrets 저장용 변수
    private AwsSecrets awsSecretsInstance;

    /**
     * AWS Secrets Manager의 시크릿을 처리하는 BeanFactoryPostProcessor를 생성합니다.
     *
     * BeanFactoryPostProcessor는 스프링 컨테이너가 Bean을 생성하기 전에 실행되어
     * 설정을 동적으로 변경할 수 있게 합니다. 이를 통해 AWS Secrets Manager에서
     * 가져온 DB 연결 정보와 OpenAI API 키를 애플리케이션 설정에 주입합니다.
     *
     * @param environment 스프링 환경 설정 객체
     * @return 시크릿을 처리하는 BeanFactoryPostProcessor
     */
    @Bean
    public BeanFactoryPostProcessor awsSecretsProcessor(ConfigurableEnvironment environment) {
        return (ConfigurableListableBeanFactory beanFactory) -> {
            if (isTestEnvironment(environment)) {
                logger.info("Detected test environment - skip loading AWS Secrets.");
                this.awsSecretsInstance = createDummyAwsSecrets();

                Properties dummyProps = new Properties();
                dummyProps.setProperty(OPENAI_API_KEY_PROPERTY, "test");
                environment.getPropertySources()
                        .addFirst(new PropertiesPropertySource(PROPERTY_SOURCE_NAME, dummyProps));
                return; // 더 이상 진행하지 않음
            }

            try {
                // 시크릿 JSON 로드
                String secretJson = loadSecrets(environment);

                // JSON 파싱
                @SuppressWarnings("unchecked")
                Map<String, String> secretsMap = objectMapper.readValue(secretJson, Map.class);

                // AwsSecrets 인스턴스 생성
                this.awsSecretsInstance = createAwsSecrets(secretsMap);

                // 설정 적용
                Properties properties = createApplicationProperties(secretsMap, environment);

                // 시크릿을 애플리케이션 설정에 동적으로 추가 (최우선 적용)
                environment.getPropertySources().addFirst(
                        new PropertiesPropertySource(PROPERTY_SOURCE_NAME, properties)
                );

                logger.info("AWS secrets successfully loaded and applied");
            } catch (Exception e) {
                throw new ExternalServiceException(EXTERNAL_SERVICE_EXCEPTION, "", e);
            }
        };
    }

    /**
     * AwsSecrets Bean을 생성합니다.
     */
    @Bean
    public AwsSecrets awsSecrets() {
        if (awsSecretsInstance == null) {
            throw new IllegalStateException("AwsSecrets has not been initialized. Make sure awsSecretsProcessor runs first.");
        }
        return awsSecretsInstance;
    }

    /**
     * 환경에 따라 시크릿을 로드하는 방법을 결정합니다.
     *
     * @param environment 스프링 환경 설정 객체
     * @return 시크릿 JSON 문자열
     */
    private String loadSecrets(ConfigurableEnvironment environment) {
        String[] activeProfiles = environment.getActiveProfiles();
        boolean isLocal = activeProfiles.length > 0 && DEV_PROFILE.equals(activeProfiles[0]);

        if (isLocal) {
            // 로컬 환경에서는 AWS Secrets Manager에서 직접 로드
            return loadFromSecretsManager(environment);
        } else {
            // 그 외 환경에서는 환경 변수에서 로드 (ECS 제공)
            return loadEnvironmentSecrets();
        }
    }

    /**
     * AWS Secrets Manager에서 시크릿을 로드합니다.
     *
     * 로컬 개발 환경에서는 AWS 프로필을 사용하여 Secrets Manager에 접근하고,
     * 지정된 시크릿 이름으로 모든 보안 정보를 가져옵니다.
     *
     * @param environment 스프링 환경 설정 객체
     * @return 시크릿 JSON 문자열
     */
    private String loadFromSecretsManager(ConfigurableEnvironment environment) {
        // 시크릿 이름 (모든 환경에서 'bootme' 사용)
        String secretName = environment.getProperty(APP_AWS_SECRETS_MANAGER_SECRET_NAME);
        if (secretName == null || secretName.isEmpty()) {
            throw new ValidationException(MISSING_CONFIGURATION, APP_AWS_SECRETS_MANAGER_SECRET_NAME);
        }

        // AWS 프로필 설정
        String awsProfile = environment.getProperty(APP_AWS_PROFILE);
        logger.info("Loading secrets from AWS Secrets Manager using profile '{}': {}", awsProfile, secretName);

        // AWS 리전 설정
        String regionStr = environment.getProperty(APP_AWS_REGION, DEFAULT_REGION);
        Region region = Region.of(regionStr);

        try {
            // AWS 자격 증명 공급자 설정
            ProfileCredentialsProvider.Builder credentialsProviderBuilder = ProfileCredentialsProvider.builder();
            if (awsProfile != null && !awsProfile.isEmpty()) {
                credentialsProviderBuilder.profileName(awsProfile);
            }
            ProfileCredentialsProvider credentialsProvider = credentialsProviderBuilder.build();

            // Secrets Manager 클라이언트 생성
            SecretsManagerClient secretsClient = SecretsManagerClient.builder()
                    .region(region)
                    .credentialsProvider(credentialsProvider)
                    .build();

            // 시크릿 값 요청 - ARN 대신 이름 사용
            GetSecretValueRequest valueRequest = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();

            // 시크릿 문자열 반환
            String secretString = secretsClient.getSecretValue(valueRequest).secretString();
            logger.info("Successfully retrieved secrets from AWS Secrets Manager");
            return secretString;
        } catch (Exception e) {
            logger.error("Failed to load secrets from AWS Secrets Manager: {}", e.getMessage());
            throw new ExternalServiceException(EXTERNAL_SERVICE_EXCEPTION, "", e);
        }
    }

    /**
     * 환경 변수에서 시크릿 JSON을 로드합니다.
     *
     * AWS 시크릿 매니저에 저장된 시크릿 값이 ECS 태스크 정의의 containerDefinitions.secrets 항목에서 참조되어
     * 컨테이너의 환경 변수로 주입됩니다. 컨테이너 시작 시 ECS 서비스가 해당 시크릿을 자동으로 가져와 환경 변수에 설정합니다.
     *
     * 참고: https://docs.aws.amazon.com/AmazonECS/latest/developerguide/secrets-envvar-secrets-manager.html#secrets-envvar-secrets-manager-update-container-definition
     *
     * @return 시크릿 JSON 문자열
     */
    private String loadEnvironmentSecrets() {
        String secretsJson = System.getenv(AWS_SECRETS_MANAGER_SECRETS_ENV_VAR);
        if (secretsJson == null || secretsJson.isEmpty()) {
            throw new ValidationException(MISSING_CONFIGURATION, AWS_SECRETS_MANAGER_SECRETS_ENV_VAR);
        }
        return secretsJson;
    }

    /**
     * AwsSecrets 객체를 생성합니다.
     */
    private AwsSecrets createAwsSecrets(Map<String, String> secretsMap) {
        return AwsSecrets.builder()
                .apiUrl(getRequiredSecret(secretsMap, SECRET_KEY_API_URL))
                .googleClientId(getRequiredSecret(secretsMap, SECRET_KEY_GOOGLE_CLIENT_ID))
                .googleAudience(getRequiredSecret(secretsMap, SECRET_KEY_GOOGLE_AUDIENCE))
                .naverClientId(getRequiredSecret(secretsMap, SECRET_KEY_NAVER_CLIENT_ID))
                .naverClientSecret(getRequiredSecret(secretsMap, SECRET_KEY_NAVER_CLIENT_SECRET))
                .naverAudience(getRequiredSecret(secretsMap, SECRET_KEY_NAVER_AUDIENCE))
                .naverSigningKey(getRequiredSecret(secretsMap, SECRET_KEY_NAVER_SIGNING_KEY))
                .kakaoRestApiKey(getRequiredSecret(secretsMap, SECRET_KEY_KAKAO_REST_API_KEY))
                .kakaoClientSecret(getRequiredSecret(secretsMap, SECRET_KEY_KAKAO_CLIENT_SECRET))
                .kakaoAudience(getRequiredSecret(secretsMap, SECRET_KEY_KAKAO_AUDIENCE))
                .kakaoJavascriptKey(getRequiredSecret(secretsMap, SECRET_KEY_KAKAO_JAVASCRIPT_KEY))
                .bootmeAudience(getRequiredSecret(secretsMap, SECRET_KEY_BOOTME_AUDIENCE))
                .bootmeSigningKey(getRequiredSecret(secretsMap, SECRET_KEY_BOOTME_SIGNING_KEY))
                .gaMeasurementId(getRequiredSecret(secretsMap, SECRET_KEY_GA_MEASUREMENT_ID))
                .build();
    }

    /**
     * 필수 시크릿 값을 가져옵니다.
     */
    private String getRequiredSecret(Map<String, String> secretsMap, String key) {
        String value = secretsMap.get(key);
        if (value == null || value.isEmpty()) {
            throw new ValidationException(MISSING_CONFIGURATION, key);
        }
        return value;
    }

    /**
     * 애플리케이션 프로퍼티를 생성합니다.
     *
     * AWS Secrets Manager에서 가져온 JSON을 파싱하여 Spring 설정에 필요한
     * Properties 객체를 생성합니다. 이 Properties는 spring.datasource.* 및 openai.api-key 설정값으로 변환됩니다.
     *
     * @param secretsMap 시크릿 JSON을 파싱한 Map
     * @param environment 스프링 환경 설정 객체
     * @return 애플리케이션 설정이 포함된 Properties 객체
     */
    private Properties createApplicationProperties(Map<String, String> secretsMap, ConfigurableEnvironment environment) {
        Properties properties = new Properties();

        // DB 설정
        String dbUrl = secretsMap.get(SECRET_KEY_DB_URL);
        if (dbUrl == null || dbUrl.isEmpty()) {
            throw new ValidationException(MISSING_CONFIGURATION, SECRET_KEY_DB_URL);
        }

        // OpenAI API 키
        String openAiApiKey = secretsMap.get(SECRET_KEY_OPENAI_API_KEY);
        if (openAiApiKey == null || openAiApiKey.isEmpty()) {
            throw new ValidationException(MISSING_CONFIGURATION, SECRET_KEY_OPENAI_API_KEY);
        }

        // Spring 설정값으로 변환
        properties.setProperty(SPRING_DATASOURCE_URL, dbUrl);
        properties.setProperty(OPENAI_API_KEY_PROPERTY, openAiApiKey);

        return properties;
    }

    /**
     * 테스트 환경(H2 in‑memory DB 사용) 여부를 판별
     */
    private boolean isTestEnvironment(ConfigurableEnvironment environment) {
        String datasourceUrl = environment.getProperty(SPRING_DATASOURCE_URL);
        return datasourceUrl != null && datasourceUrl.startsWith("jdbc:h2:mem");
    }

    /**
     * 테스트 전용 더미 AwsSecrets
     */
    private AwsSecrets createDummyAwsSecrets() {
        return AwsSecrets.builder()
                .apiUrl("http://localhost")
                .googleClientId("dummy")
                .googleAudience("dummy")
                .naverClientId("dummy")
                .naverClientSecret("dummy")
                .naverAudience("dummy")
                .naverSigningKey("dummy")
                .kakaoRestApiKey("dummy")
                .kakaoClientSecret("dummy")
                .kakaoAudience("dummy")
                .kakaoJavascriptKey("dummy")
                .bootmeAudience("dummy")
                .bootmeSigningKey("dummy")
                .gaMeasurementId("dummy")
                .build();
    }

}
