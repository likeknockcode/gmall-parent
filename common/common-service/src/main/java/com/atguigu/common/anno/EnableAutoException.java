package com.atguigu.common.anno;

import com.atguigu.common.exception.GlobalExceptionHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description：全局异常处理注解
 * @Author: scv
 * @CreateTime: 2023-07-11  19:19
 * @Version: 1.0
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import(GlobalExceptionHandler.class)
public @interface EnableAutoException {

}
