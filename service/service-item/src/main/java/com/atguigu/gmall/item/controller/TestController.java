package com.atguigu.gmall.item.controller;

import com.atguigu.gmall.common.result.Result;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Description：测试类
 * @Author: scv
 * @CreateTime: 2023-07-18  21:29
 * @Version: 1.0
 */
@RestController
@RequestMapping("api/inner/item")
public class TestController {
    @Autowired
    private RedissonClient redissonClient;
    @GetMapping(value = "/testLock01")
    public Result testLock01() throws InterruptedException {

        // 加锁
        RLock lock = redissonClient.getLock("redisson-lock");

        // 一直等待获取锁，直到获取到锁为止! 默认锁的存活时间为30s
        // lock.lock();
        // lock.lock(10 , TimeUnit.SECONDS);       // 指定锁的过期时间为10s
        // boolean tryLock = lock.tryLock();          // 尝试获取锁，如果可以获取到锁就返回true，否则返回false， 默认情况下锁的过期时间为30s
        boolean tryLock = lock.tryLock(3, 20, TimeUnit.SECONDS);  // 指定加锁的等待超时时间为3s
        if(tryLock) {

            // 获取到了锁
            System.out.println("获取到了锁" + Thread.currentThread().getName());

            // 执行业务操作
            System.out.println("执行业务操作");
            TimeUnit.SECONDS.sleep(10);

            // 释放锁
            lock.unlock();
            System.out.println("释放了锁" + Thread.currentThread().getName());

        }else {
            System.out.println("没有获取到锁---->" + Thread.currentThread().getName() );
        }

        // 返回结果
        return Result.ok() ;

    }
}
