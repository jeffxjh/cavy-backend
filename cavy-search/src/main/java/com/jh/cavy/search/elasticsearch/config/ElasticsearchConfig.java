package com.jh.cavy.search.elasticsearch.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.jh.cavy.search.elasticsearch.repositories")
public class ElasticsearchConfig {
    @Value("${es.address}")
    private String esAddress;
    @Bean
    RestHighLevelClient elasticsearchClient() {
        ClientConfiguration configuration = ClientConfiguration.builder()
                                                    .connectedTo(esAddress)
                                                    //.withConnectTimeout(Duration.ofSeconds(5))
                                                    //.withSocketTimeout(Duration.ofSeconds(3))
                                                    //.useSsl()
                                                    //.withDefaultHeaders(defaultHeaders)
                                                    //.withBasicAuth(username, password)
                                                    // ... other options

                                                    .build();
        return RestClients.create(configuration).rest();
    }
}
