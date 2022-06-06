package com.jh.cavy.file.minio.config;

import com.jh.cavy.file.minio.utils.MinioUtil;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration

public class MinioConfig {
    @Resource
    private MinioProperties minioProperties;

    @Bean
    @ConditionalOnProperty(name = "file.type", havingValue = "minio")
    public MinioClient minioClient() {
        return MinioClient.builder().endpoint(minioProperties.getEndpoint())
                       .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build();
    }

    @Bean
    public MinioUtil minioUtil(MinioProperties minioProperties, @Autowired(required = false) MinioClient minioClient) {
        if (minioClient==null)
            return null;
        return new MinioUtil(minioProperties, minioClient);
    }
}
