package com.atguigu.common.config;

import com.atguigu.common.properties.MiniProperties;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description：MinioClient配置类
 * @Author: scv
 * @CreateTime: 2023-07-11  20:27
 * @Version: 1.0
 */
@Configuration
@EnableConfigurationProperties(value = {MiniProperties.class})
public class MinioConfigration {
    @Autowired
    private MiniProperties miniProperties;


    @Bean
    public MinioClient createMinioClient(){
        MinioClient minioClient = MinioClient.builder().endpoint(miniProperties.getEndpoint())
                .credentials(miniProperties.getAccessKey(), miniProperties.getSecretKey())
                .build();
        return minioClient;
    }
}
