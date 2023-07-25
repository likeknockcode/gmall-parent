package com.atguigu.gmall.common.feign.product;

import com.atguigu.gmall.common.feign.product.fallback.SkuDetailFeignClientFallBack;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.entity.SkuInfo;
import com.atguigu.gmall.product.entity.SpuSaleAttr;
import com.atguigu.gmall.product.vo.AttrValueConcat;
import com.atguigu.gmall.product.vo.CategoryView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "service-product",fallback = SkuDetailFeignClientFallBack.class)
public interface SkuDetailFeignClient {
    @GetMapping("api/inner/product/category/{skuId}")
    public Result<CategoryView> findCategoryBySkuId(@PathVariable("skuId") Long skuId);

    @GetMapping(value = "api/inner/product/skuInfo/{skuId}")
    public Result<SkuInfo> findSkuInfoAndImageBySkuId(@PathVariable("skuId") Long skuId);

    @GetMapping(value = "api/inner/product/skuBaseInfo/{skuId}")
    public Result<SkuInfo> findSkuById(@PathVariable(value = "skuId") Long skuId);

    @GetMapping(value = "api/inner/product/findSpuSalAttrAndValueBySkuId/{skuId}")
    public Result<List<SpuSaleAttr>> findSpuSalAttrAndValueBySkuId(@PathVariable(value = "skuId") Long skuId);

    @GetMapping(value = "api/inner/product/findAttrValueConcatBySkuId/{skuId}")
    public  Result<List<AttrValueConcat>> findAttrValueConcatBySkuId(@PathVariable("skuId") Long skuId);

    @GetMapping(value = "api/inner/product//findSkuIdList")
    public Result<List<SkuInfo>> findSkuIdList();
}
