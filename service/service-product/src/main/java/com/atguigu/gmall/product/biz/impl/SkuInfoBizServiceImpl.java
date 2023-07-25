package com.atguigu.gmall.product.biz.impl;

import com.atguigu.gmall.common.constant.GmallConstant;
import com.atguigu.gmall.product.biz.SkuInfoBizService;
import com.atguigu.gmall.product.entity.SkuInfo;
import com.atguigu.gmall.product.mapper.SkuInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Description：SkuInfoBizService实现类
 * @Author: scv
 * @CreateTime: 2023-07-14  23:42
 * @Version: 1.0
 */
@Service
@Slf4j
public class SkuInfoBizServiceImpl implements SkuInfoBizService {
    @Autowired
    private SkuInfoMapper skuInfoMapper;
    @Override
    public SkuInfo findSkuInfoAndImageBySkuId(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.findSkuInfoAndImageBySkuId(skuId);
        return skuInfo;
    }

    @Override
    public SkuInfo getById(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        return skuInfo;
    }

    @Override
    public List<SkuInfo> findSkuIdList() {
        List<SkuInfo> skuInfoList = skuInfoMapper.selectList(null);
        return skuInfoList;
    }

    @Autowired
    private RedissonClient redissonClient;
    @PostConstruct
    public void initBloomFilter(){
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(GmallConstant.REDIS_SKUID_BLOOM_FILTER);
        bloomFilter.tryInit(1000000,0.000001);
        List<SkuInfo> skuIdList = findSkuIdList();
        skuIdList.forEach(skuInfo -> {
                bloomFilter.add(skuInfo.getId());
        });
        log.info("布隆过滤器初始化成功");
    }
}
