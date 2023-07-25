package com.atguigu.gmall.common.cache.aspect;


import com.atguigu.gmall.common.cache.anno.GmallCache;
import com.atguigu.gmall.common.cache.service.RedisCacheService;
import com.atguigu.gmall.common.constant.GmallConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * @Description：缓存切面
 * @Author: scv
 * @CreateTime: 2023-07-21  19:28
 * @Version: 1.0
 */

@Slf4j
@Aspect
public class GmallCacheAspect {
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisCacheService redisCacheService;
    @Around("@annotation(gmallCache)")
    public Object aroundCacheAdvice(ProceedingJoinPoint point, GmallCache gmallCache){
        //获取分布式布隆过滤器
        String bloomFilterName = paraseExpression(point, gmallCache.bloomFilterName(), String.class);
        if (!StringUtils.isEmpty(bloomFilterName)){
            Object bloomFilterValue = paraseExpression(point, gmallCache.bloomFilterValue(), Object.class);
            if (bloomFilterValue != null){
                RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(bloomFilterName);
                if (!bloomFilter.contains(bloomFilterValue)){
                    log.error("分布式布隆过滤器中不存在要查询的数据------- 直接返回null");
                    return null;
                }
            }
        }
        //获取reids的key数据

        String cacheKeyExpression = gmallCache.cacheKey();

        String cacheKey = paraseExpression(point, cacheKeyExpression, String.class);
        Type returnType = getMethodReturnType(point);
        //查询redis缓存
        Object result = redisCacheService.getData(cacheKey, returnType);
        if (result != null ){
            log.info("从redis中命中缓存-----------,直接返回");
            return  result;
        }
        boolean enableLock = gmallCache.enableLock();
        if (enableLock){
            //添加分布式锁
            String lockName = paraseExpression(point, gmallCache.lockName(), String.class);
            RLock rLock = redissonClient.getLock(lockName);
            boolean lock = rLock.tryLock();
            if (lock){
                try {
                    //发送远程调用查询数据库
                    result = point.proceed();
                    if (result == null){
                        log.info("查询数据库，数据库不存在数据缓存X值-------------");
                        redisCacheService.saveDataRedis(cacheKey, GmallConstant.DB_NULL_CATEGORY_VALUE);
                    }else {
                        log.info("查询数据库，数据库中查询到的数据缓存到redis中,skuId:{}");
                        redisCacheService.saveDataRedis(cacheKey,result);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }finally {
                    rLock.unlock();
                    log.info("释放分布式锁！！");
                }
                return result;
            }else {
                //查询redis缓存
                result = redisCacheService.getData(cacheKey, returnType);

                if (result != null){
                    return result;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return redisCacheService.getData(cacheKey, returnType);
            }
        }else {
            try {
                result = point.proceed();
                log.info("从数据库中查询到了数据，并进行返回...");
                redisCacheService.saveDataRedis(cacheKey,result);
                return result;
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    public Type getMethodReturnType(ProceedingJoinPoint point){
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        Type returnType = method.getGenericReturnType();
        return returnType;
    }
    public <T> T paraseExpression(ProceedingJoinPoint proceedingJoinPoint , String cacheKey , Class<T>  clazz) {
        Expression expression = spelExpressionParser.parseExpression(cacheKey, ParserContext.TEMPLATE_EXPRESSION);
        EvaluationContext evaluationContext = new StandardEvaluationContext() ;
        evaluationContext.setVariable("params" , proceedingJoinPoint.getArgs());
        T cacheKeyData = expression.getValue(evaluationContext, clazz);
        return cacheKeyData ;
    }


}
