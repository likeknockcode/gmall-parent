package com.atguigu.gmall.common.feign.search.fallback;

import com.atguigu.gmall.common.feign.search.GoodsFeignClient;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.search.Goods;
import com.atguigu.gmall.search.SearchParamDTO;
import com.atguigu.gmall.search.SearchResponseVo;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description：降级类
 * @Author: scv
 * @CreateTime: 2023-07-22  23:52
 * @Version: 1.0
 */

@Slf4j
public class GoodsFeignClientFallBack implements GoodsFeignClient {
    @Override
    public Result saveGoods(Goods goods) {
        log.error("GoodsFeignClientFallback....saveGoods降级方法执行了...");
        return Result.ok();
    }

    @Override
    public Result deleteById(Long skuId) {
        log.error("GoodsFeignClientFallback....deleteById降级方法执行了...");
        return Result.ok();
    }

    @Override
    public Result<SearchResponseVo> search(SearchParamDTO searchParamDTO) {
        log.error("GoodsFeignClientFallback....search降级方法执行了...");
        return Result.ok();
    }

    @Override
    public Result updateHotScore(Long goodsId, Integer hotScore) {
        log.error("GoodsFeignClientFallback....updateHotScore降级方法执行了...");
        return Result.ok();
    }
}
