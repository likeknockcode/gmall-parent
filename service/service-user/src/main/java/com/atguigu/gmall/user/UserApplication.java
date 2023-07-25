package com.atguigu.gmall.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description：Servcie-User启动类
 * @Author: scv
 * @CreateTime: 2023-07-24  15:35
 * @Version: 1.0
 */
@SpringBootApplication
@MapperScan(basePackages = "com.atguigu.gmall.user.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
