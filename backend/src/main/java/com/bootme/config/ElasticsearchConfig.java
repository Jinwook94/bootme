package com.bootme.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.bootme.post.repository")
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    // elasticsearch.host 정의되어 있으면 그 값을 사용하고, 정의되어 있지 않으면 localhost 사용
    @Value("${elasticsearch.host:localhost}")
    private String elasticsearchHost;

    @Value("${elasticsearch.port:9200}")
    private int elasticsearchPort;

    @Value("${elasticsearch.username:}")
    private String elasticsearchUsername;

    @Value("${elasticsearch.password:}")
    private String elasticsearchPassword;

    @Override
    public ClientConfiguration clientConfiguration() {
        ClientConfiguration.MaybeSecureClientConfigurationBuilder builder = ClientConfiguration.builder()
                .connectedTo(elasticsearchHost + ":" + elasticsearchPort);

        // 사용자 이름과 비밀번호가 모두 설정된 경우에만 기본 인증 추가
        if (elasticsearchUsername != null && !elasticsearchUsername.isEmpty() &&
                elasticsearchPassword != null && !elasticsearchPassword.isEmpty()) {
            builder.withBasicAuth(elasticsearchUsername, elasticsearchPassword);
        }

        return builder.build();
    }
}
