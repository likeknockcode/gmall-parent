package com.atguigu.gmall.product;

import com.atguigu.common.anno.*;

import com.atguigu.gmall.common.cache.anno.EnableRedissonClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@MapperScan(basePackages = "com.atguigu.gmall.product.mapper")
@EnableAutoException
@EnableFileUpload
@EnableSwagger2Configration
@EnableScheduling
@EnableRedissonClient
@EnableThreadPool
@EnableFeignClients(basePackages = {
        "com.atguigu.gmall.common.feign.search"
})
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
