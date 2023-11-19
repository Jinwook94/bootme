package com.bootme.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.bootme.post.repository")
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Value("${elasticsearch.host:10.0.6.174}")
    private String elasticsearchHost;

    @Value("${elasticsearch.port:9200}")
    private int elasticsearchPort;

    @Value("${elasticsearch.username}")
    private String elasticsearchUsername;

    @Value("${elasticsearch.password}")
    private String elasticsearchPassword;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticsearchHost + ":" + elasticsearchPort)
                .withBasicAuth(elasticsearchUsername, elasticsearchPassword)
                .build();
    }
}
