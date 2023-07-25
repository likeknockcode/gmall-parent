package com.atguigu.gmall.product.task;

import com.atguigu.gmall.product.service.BloomFilterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @Description：定时任务重置布隆过滤器
 * @Author: scv
 * @CreateTime: 2023-07-21  18:53
 * @Version: 1.0
 */
@Service
@Slf4j
public class ResetBloomFilterTimeTask {
    @Autowired
    private BloomFilterService bloomFilterService;
    @Scheduled(cron = "0 0 3 */7 * ?")
    public void resetBloomFilterTimeTask(){
        log.info("重置布隆过滤器定时任务执行了！");
        bloomFilterService.resetBloomFilter();
    }

}
