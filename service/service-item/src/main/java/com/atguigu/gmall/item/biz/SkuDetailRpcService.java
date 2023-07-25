package com.atguigu.gmall.item.biz;

import com.atguigu.gmall.product.vo.SkuDetailVo;

public interface SkuDetailRpcService {
    public abstract SkuDetailVo skuDetailVo(Long skuId);

    public abstract void updateHotScore(Long skuId);

}
