package com.atguigu.common.config;

import com.atguigu.common.properties.ThreadPoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @Description：自定义线程池
 * @Author: scv
 * @CreateTime: 2023-07-22  00:33
 * @Version: 1.0
 */
@Configuration
@EnableConfigurationProperties(value = ThreadPoolProperties.class)
public class ThreadPoolConfiguration {

    @Autowired
    private ThreadPoolProperties threadPoolProperties;
    @Value("${spring.application.name}")
    private String applicationName;
    //自定义线程池
    @Bean
    public ThreadPoolExecutor threadPoolExecutor(){
        return new ThreadPoolExecutor(threadPoolProperties.getCorePoolSize(), threadPoolProperties.getMaximumPoolSize(), threadPoolProperties.getKeepAliveTime(),
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(threadPoolProperties.getWorkQueueSize()),
                new ThreadFactory() {
                    int number = 1;
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("【"+applicationName+"】-threadpool"+number++);
                        return thread;
                    }
                },
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
