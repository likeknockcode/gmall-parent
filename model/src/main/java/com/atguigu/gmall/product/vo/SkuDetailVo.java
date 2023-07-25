package com.atguigu.gmall.product.vo;

import com.atguigu.gmall.product.entity.SkuInfo;
import com.atguigu.gmall.product.entity.SpuSaleAttr;
import com.atguigu.gmall.product.entity.SpuSaleAttrValue;
import lombok.Data;

import java.util.List;

/**
 * @Description：封装前端所需数据实体类
 * @Author: scv
 * @CreateTime: 2023-07-14  21:16
 * @Version: 1.0
 */
@Data
public class SkuDetailVo {
    private CategoryView categoryView;
    private SkuInfo skuInfo;
    private Integer price;
    private List<SpuSaleAttr> spuSaleAttrList;
    private String valuesSkuJson;

}
