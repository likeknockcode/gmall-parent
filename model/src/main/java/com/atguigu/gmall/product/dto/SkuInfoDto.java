package com.atguigu.gmall.product.dto;

import com.atguigu.gmall.product.entity.SkuAttrValue;
import com.atguigu.gmall.product.entity.SkuImage;
import com.atguigu.gmall.product.entity.SkuSaleAttrValue;
import javafx.animation.ParallelTransition;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description：SkuDto实体类
 * @Author: scv
 * @CreateTime: 2023-07-13  00:45
 * @Version: 1.0
 */
@Data
public class SkuInfoDto {
    private Long id;
    private Long spuId;
    private Integer price;
    private String skuName;
    private BigDecimal weight;
    private String skuDesc;
    private Long category3Id;
    private String skuDefaultImg;
    private Long tmId;

    private List<SkuAttrValue> skuAttrValueList;

    private List<SkuSaleAttrValue> skuSaleAttrValueList;

    private List<SkuImage> skuImageList;
}
