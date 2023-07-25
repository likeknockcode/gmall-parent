package com.atguigu.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description：线程池参数通过文件配置
 * @Author: scv
 * @CreateTime: 2023-07-22  15:48
 * @Version: 1.0
 */
@Data
@ConfigurationProperties(prefix = "app.threadpool")
public class ThreadPoolProperties {
    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime;      // 以毫秒为单位
    private int workQueueSize;
}
