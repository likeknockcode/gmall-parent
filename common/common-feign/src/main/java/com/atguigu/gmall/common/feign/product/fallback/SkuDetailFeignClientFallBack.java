package com.atguigu.gmall.common.feign.product.fallback;

import com.atguigu.gmall.common.feign.product.SkuDetailFeignClient;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.entity.SkuInfo;
import com.atguigu.gmall.product.entity.SpuSaleAttr;
import com.atguigu.gmall.product.vo.AttrValueConcat;
import com.atguigu.gmall.product.vo.CategoryView;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Description：服务降级
 * @Author: scv
 * @CreateTime: 2023-07-14  23:11
 * @Version: 1.0
 */
@Slf4j
public class SkuDetailFeignClientFallBack implements SkuDetailFeignClient {

    @Override
    public Result<CategoryView> findCategoryBySkuId(Long skuId) {
        log.error("SkuDetailFeignClientFallBack...findCategoryBySkuId执行了...");
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Override
    public Result<SkuInfo> findSkuInfoAndImageBySkuId(Long skuId) {
        log.error("SkuDetailFeignClientFallBack...findSkuInfoAndImageBySkuId执行了...");
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Override
    public Result<SkuInfo> findSkuById(Long skuId) {
        log.error("SkuDetailFeignClientFallBack...findSkuById执行了...");
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Override
    public Result<List<SpuSaleAttr>> findSpuSalAttrAndValueBySkuId(Long skuId) {
        log.error("SkuDetailFeignClientFallBack...findSpuSalAttrAndValueBySkuId执行了...");
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Override
    public Result<List<AttrValueConcat>> findAttrValueConcatBySkuId(Long skuId) {
        log.error("SkuDetailFeignClientFallBack...findAttrValueConcatBySkuId执行了...");
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Override
    public Result<List<SkuInfo>> findSkuIdList() {
        log.error("SkuDetailFeignClientFallBack...findSkuIdList执行了...");
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


}
