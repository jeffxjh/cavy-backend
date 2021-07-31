package com.jh.cavyfile.minio.config;

import com.jh.cavyfile.minio.utils.MinioUtil;
import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@ConditionalOnProperty(name = "file.type", havingValue = "minio")
public class MinioConfig {
    @Resource
    private MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder().endpoint(minioProperties.getEndpoint())
                       .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build();
    }

    @Bean
    public MinioUtil minioUtil(MinioProperties minioProperties, MinioClient minioClient) {
        return new MinioUtil(minioProperties, minioClient);
    }
}
