package com.atguigu.gmall.common.feign.product;

import com.atguigu.gmall.common.feign.product.fallback.BaseCategoryFeignClientFallBack;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.vo.CategoryVo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Description：远程调用Feign接口
 * @Author: scv
 * @CreateTime: 2023-07-14  20:22
 * @Version: 1.0
 */
@FeignClient(value = "service-product",fallback = BaseCategoryFeignClientFallBack.class)
public interface BaseCategoryFeignClient {
    @GetMapping("api/inner/product/categorys/tree")
    public Result<List<CategoryVo>> findAllCategoryTree();
}
