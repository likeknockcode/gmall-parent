package com.atguigu.gmall.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description：AdminApplication启动类
 * @Author: scv
 * @CreateTime: 2023-07-16  18:13
 * @Version: 1.0
 */
@SpringBootApplication
@EnableAdminServer
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class , args) ;
    }
}
