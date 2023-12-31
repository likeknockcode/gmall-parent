package com.atguigu.gmall.common.cache.anno;

import com.atguigu.gmall.common.cache.aspect.GmallCacheAspect;
import com.atguigu.gmall.common.cache.config.RedissonConfiguration;
import com.atguigu.gmall.common.cache.service.impl.RedisCacheServiceImpl;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import(value = {GmallCacheAspect.class , RedissonConfiguration.class, RedisCacheServiceImpl.class})
public @interface EnableRedissonClient {
}
