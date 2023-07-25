package com.atguigu.gmall.common.feign.search;

import com.atguigu.gmall.common.feign.search.fallback.GoodsFeignClientFallBack;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.search.Goods;
import com.atguigu.gmall.search.SearchParamDTO;
import com.atguigu.gmall.search.SearchResponseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "service-search",fallback = GoodsFeignClientFallBack.class)
public interface GoodsFeignClient {
    @PostMapping("/api/inner/search/saveGoods")
    public abstract Result saveGoods(@RequestBody Goods goods);

    @DeleteMapping("/api/inner/search/deleteById/{skuId}")
    public abstract Result deleteById(@PathVariable("skuId") Long skuId);
    @PostMapping("/api/inner/search/searchGoods")
    public abstract Result<SearchResponseVo> search(@RequestBody SearchParamDTO searchParamDTO);
    @PutMapping("/api/inner/search/updateHotScore")
    public Result updateHotScore(@RequestParam("goodsId") Long goodsId,@RequestParam("hotScore") Integer hotScore);
}
