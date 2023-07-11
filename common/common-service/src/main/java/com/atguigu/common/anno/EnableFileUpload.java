package com.atguigu.common.anno;

import com.atguigu.common.config.MinioConfigration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @description: 文件上传注解
 * @author: scv
 * @date: 2023/7/12 0:50
 * @param: null
 * @return: null
 **/
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import(value = MinioConfigration.class)
public @interface EnableFileUpload {
}
