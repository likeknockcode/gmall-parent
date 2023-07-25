package com.atguigu.gmall.common.feign.item.fallback;

import com.atguigu.gmall.common.feign.item.SkuDetailFeignClient;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.vo.SkuDetailVo;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description：服务降级
 * @Author: scv
 * @CreateTime: 2023-07-14  21:40
 * @Version: 1.0
 */
@Slf4j
public class SkuDetailFeignClientFallBack implements SkuDetailFeignClient {
    @Override
    public Result<SkuDetailVo> skuDetailVo(Long skuId) {
        log.error("BaseCategoryFeignClientFallBack...findAllCategoryTree执行了...");
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
