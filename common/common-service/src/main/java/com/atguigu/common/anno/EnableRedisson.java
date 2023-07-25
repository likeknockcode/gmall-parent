package com.atguigu.common.anno;

import com.atguigu.common.config.RedissonConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import(value = RedissonConfiguration.class)
public @interface EnableRedisson {

}
