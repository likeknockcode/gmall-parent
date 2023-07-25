package com.atguigu.gmall.product.biz;

import com.atguigu.gmall.product.entity.SkuInfo;
import javafx.scene.control.SkinBase;

import java.util.List;

public interface SkuInfoBizService {
    public abstract SkuInfo findSkuInfoAndImageBySkuId(Long skuId);

    public abstract SkuInfo getById(Long skuId);

    public abstract List<SkuInfo> findSkuIdList();

}
