package com.atguigu.gmall.common.cache.service;

import java.lang.reflect.Type;

/**
 * @Description：公共缓存service
 * @Author: scv
 * @CreateTime: 2023-07-18  23:40
 * @Version: 1.0
 */
public interface RedisCacheService {
    /**
     * @description: 获取缓存数据的方法
     * @author: scv
     * @date: 2023/7/18 23:43
     * @param: key
     * @param: clazz
     * @return: T
     **/
    public abstract <T> T getData(String key, Type type);
    public abstract <T> T getData(String key, Class<T> clazz);
    /**
     * @description: 设置缓存数据的方法
     * @author: scv
     * @date: 2023/7/18 23:44
     * @param: key
     * @param: data
     **/
    public abstract void saveDataRedis(String key,Object data);
}
