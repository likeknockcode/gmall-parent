package com.atguigu.gmall.common.feign.item;

import com.atguigu.gmall.common.feign.item.fallback.SkuDetailFeignClientFallBack;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.vo.SkuDetailVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-item",fallback = SkuDetailFeignClientFallBack.class)
public interface SkuDetailFeignClient {
    @GetMapping("api/inner/item/detail/{skuId}")
    public Result<SkuDetailVo> skuDetailVo(@PathVariable("skuId") Long skuId);
}
