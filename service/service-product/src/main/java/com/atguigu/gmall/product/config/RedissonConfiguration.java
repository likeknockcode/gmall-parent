package com.atguigu.gmall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description：redisson配置类
 * @Author: scv
 * @CreateTime: 2023-07-18  21:24
 * @Version: 1.0
 */
@Configuration
public class RedissonConfiguration {
    @Autowired
    private RedisProperties redisProperties;
    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://"+redisProperties.getHost()+":"+redisProperties.getPort())
                .setPassword(redisProperties.getPassword());
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
