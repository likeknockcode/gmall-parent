package com.atguigu.gmall.product;

import com.atguigu.common.anno.EnableAutoException;

import com.atguigu.common.anno.EnableFileUpload;
import com.atguigu.common.properties.MiniProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@MapperScan(basePackages = "com.atguigu.gmall.product.mapper")
@EnableAutoException
@EnableFileUpload
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
