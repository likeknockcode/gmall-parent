package com.atguigu.gmall.common.cache.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface GmallCache {
    /*
    * 指定缓存key
    * */
    public String cacheKey();
    /*
    * 布隆过滤器
    * */

    public String bloomFilterName() default "" ;            // 让用户来设定bloomFilter的名称
    public String bloomFilterValue() default "" ;          // 设置布隆过滤器判定的值

    public String lockName() default "" ;                   // 分布式锁的名称
    public boolean enableLock() default false ;              // 是否需要开启分布式锁
}
