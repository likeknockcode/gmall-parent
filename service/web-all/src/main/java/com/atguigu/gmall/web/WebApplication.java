package com.atguigu.gmall.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description：web-all启动类
 * @Author: scv
 * @CreateTime: 2023-07-14  18:56
 * @Version: 1.0
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients(basePackages = {
        "com.atguigu.gmall.common.feign.product",
        "com.atguigu.gmall.common.feign.item",
        "com.atguigu.gmall.common.feign.search"
})
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class,args);
    }
}
