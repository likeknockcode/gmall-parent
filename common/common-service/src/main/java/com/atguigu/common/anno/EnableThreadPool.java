package com.atguigu.common.anno;

import com.atguigu.common.config.ThreadPoolConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import(value = {ThreadPoolConfiguration.class})
public @interface EnableThreadPool {
}
