package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.service.BloomFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description：重置布隆过滤器接口
 * @Author: scv
 * @CreateTime: 2023-07-21  18:56
 * @Version: 1.0
 */
@RestController
@RequestMapping("admin/product")
public class BloomFilterController {
    @Autowired
    private BloomFilterService bloomFilterService;

    @GetMapping("/resetBloomFilter")
    public Result resultBloomFilter(){
        bloomFilterService.resetBloomFilter();
        return Result.ok("重置布隆过滤器成功！");
    }
}
