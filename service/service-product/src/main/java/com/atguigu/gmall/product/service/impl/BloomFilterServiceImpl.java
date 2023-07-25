package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.common.constant.GmallConstant;
import com.atguigu.gmall.product.entity.SkuInfo;
import com.atguigu.gmall.product.mapper.SkuInfoMapper;
import com.atguigu.gmall.product.service.BloomFilterService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Description：布隆过滤器实现类
 * @Author: scv
 * @CreateTime: 2023-07-19  16:53
 * @Version: 1.0
 */
@Service
@Slf4j
public class BloomFilterServiceImpl implements BloomFilterService {
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public void resetBloomFilter() {
        //查询所有skuIds的数据
        List<SkuInfo> skuInfoList = skuInfoMapper.selectList(null);
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(GmallConstant.REDIS_SKUID_BLOOM_FILTER_NEW);
        bloomFilter.tryInit(1000000,0.00001);
        skuInfoList.stream().forEach(skuInfo -> bloomFilter.add(skuInfo.getId()));

        // 添加99号商品数据进行bloomFilter的区分
        bloomFilter.add(99L) ;

        // 删除原有的bloomFilter以及配置、重命名新的bloomFilter以及配置
        String script = "redis.call(\"del\" , KEYS[1])\n" +
                "redis.call(\"del\" , \"{\"..KEYS[1]..\"}:\"..\"config\")\n" +
                "redis.call(\"rename\" , KEYS[2] , KEYS[1])\n" +
                "redis.call(\"rename\" , \"{\"..KEYS[2]..\"}:\"..\"config\" , \"{\"..KEYS[1]..\"}:\"..\"config\")\n" +
                "return 1" ;

        //执行lua脚本
        redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Arrays.asList(GmallConstant.REDIS_SKUID_BLOOM_FILTER,GmallConstant.REDIS_SKUID_BLOOM_FILTER_NEW));

        //打印重置成功日志
        log.info("reset bloomFilter重置成功");



    }
}
