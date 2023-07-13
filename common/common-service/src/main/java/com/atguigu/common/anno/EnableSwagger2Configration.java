package com.atguigu.common.anno;

import com.atguigu.common.config.Swagger2Config;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description：Swagger开启注解
 * @Author: scv
 * @CreateTime: 2023-07-12  09:45
 * @Version: 1.0
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import(value = Swagger2Config.class)
public @interface EnableSwagger2Configration {
}
