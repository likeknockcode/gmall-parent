package com.atguigu.gmall.canal;

import com.xpand.starter.canal.annotation.EnableCanalClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Description：service-canal启动类
 * @Author: scv
 * @CreateTime: 2023-07-18  20:22
 * @Version: 1.0
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableCanalClient
public class CanalClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(CanalClientApplication.class,args);
    }
}
