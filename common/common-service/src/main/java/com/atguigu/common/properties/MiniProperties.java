package com.atguigu.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @Description：
 * @Author: scv
 * @CreateTime: 2023-07-11  20:28
 * @Version: 1.0
 */
@Data
@ConfigurationProperties(prefix = "app.minio")
public class MiniProperties {
    private String endpoint;
    private String bucketName;
    private String secretKey;
    private String accessKey;
}
