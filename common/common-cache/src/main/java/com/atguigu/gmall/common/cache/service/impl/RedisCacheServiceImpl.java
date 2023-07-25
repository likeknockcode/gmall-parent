package com.atguigu.gmall.common.cache.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.common.cache.service.RedisCacheService;
import com.atguigu.gmall.common.constant.GmallConstant;
import com.atguigu.gmall.common.execption.GmallException;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Type;

/**
 * @Description：公共缓存实现类
 * @Author: scv
 * @CreateTime: 2023-07-18  23:41
 * @Version: 1.0
 */
public class RedisCacheServiceImpl implements RedisCacheService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public <T> T getData(String key, Class<T> clazz) {
        String dataJSON = redisTemplate.opsForValue().get(key);
        if (GmallConstant.DB_NULL_CATEGORY_VALUE.equalsIgnoreCase(dataJSON)){
            throw new GmallException(ResultCodeEnum.REDIS_ATTACK_ERROR);
        }
        if (dataJSON == null || "".equals(dataJSON)){
            return null;
        }else {
            T t = JSON.parseObject(dataJSON, clazz);
            return t;
        }
    }

    @Override
    public Object getData(String key, Type type) {
        String dataJSON = redisTemplate.opsForValue().get(key);
        if (GmallConstant.DB_NULL_CATEGORY_VALUE.equalsIgnoreCase(dataJSON)){
            throw new GmallException(ResultCodeEnum.REDIS_ATTACK_ERROR);
        }
        if (dataJSON == null || "".equals(dataJSON)){
            return null;
        }else {
            Object object = JSON.parseObject(dataJSON, type);
            return object;
        }
    }

    @Override
    public void saveDataRedis(String key, Object data) {
        if (data!=null){
            if (GmallConstant.DB_NULL_CATEGORY_VALUE.equals(data.toString())){
                redisTemplate.opsForValue().set(key,GmallConstant.DB_NULL_CATEGORY_VALUE);
            }else {
                String jsonString = JSON.toJSONString(data);
                redisTemplate.opsForValue().set(key,jsonString);
            }
        }

    }
}
