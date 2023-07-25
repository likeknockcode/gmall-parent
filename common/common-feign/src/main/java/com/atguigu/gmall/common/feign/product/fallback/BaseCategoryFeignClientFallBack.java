package com.atguigu.gmall.common.feign.product.fallback;

import com.atguigu.gmall.common.feign.product.BaseCategoryFeignClient;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.vo.CategoryVo;

import com.atguigu.gmall.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.List;

/**
 * @Description：服务降级
 * @Author: scv
 * @CreateTime: 2023-07-14  20:25
 * @Version: 1.0
 */

@Slf4j
public class BaseCategoryFeignClientFallBack implements BaseCategoryFeignClient {
    @Override
    public Result<List<CategoryVo>> findAllCategoryTree() {
        log.error("BaseCategoryFeignClientFallBack...findAllCategoryTree执行了...");
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
