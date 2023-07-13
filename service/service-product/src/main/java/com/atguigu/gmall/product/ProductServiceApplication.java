package com.atguigu.gmall.product;

import com.atguigu.common.anno.EnableAutoException;

import com.atguigu.common.anno.EnableFileUpload;
import com.atguigu.common.anno.EnableSwagger2Configration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(basePackages = "com.atguigu.gmall.product.mapper")
@EnableAutoException
@EnableFileUpload
@EnableSwagger2Configration
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
