package com.jh.cavybackend.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

@Configuration
//@EnableElasticsearchRepositories(basePackages = "com.jh.cavybackend.elasticsearch.repositories")
public class ElasticsearchConfig {
    @Bean
    RestHighLevelClient elasticsearchClient() {
        ClientConfiguration configuration = ClientConfiguration.builder()
                                                    .connectedTo("127.0.0.1:9200")
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
