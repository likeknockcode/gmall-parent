package com.atguigu.gmall.item;

import com.atguigu.common.anno.EnableRedisson;
import com.atguigu.common.anno.EnableThreadPool;
import com.atguigu.gmall.common.cache.anno.EnableRedissonClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description：Item启动类
 * @Author: scv
 * @CreateTime: 2023-07-14  22:31
 * @Version: 1.0
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients(basePackages = {
        "com.atguigu.gmall.common.feign.product",
        "com.atguigu.gmall.common.feign.search"

})
@EnableRedissonClient
@EnableThreadPool
public class ItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class,args);
    }
}
