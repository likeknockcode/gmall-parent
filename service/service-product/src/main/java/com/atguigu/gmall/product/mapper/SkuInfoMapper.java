package com.atguigu.gmall.product.mapper;


import com.atguigu.gmall.product.entity.SkuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Light of hope
* @description 针对表【sku_info(库存单元表)】的数据库操作Mapper
* @createDate 2023-07-10 21:27:14
* @Entity com.atguigu.gmall.product.entity.SkuInfo
*/
public interface SkuInfoMapper extends BaseMapper<SkuInfo> {

    public abstract SkuInfo findSkuInfoAndImageBySkuId(Long skuId);
}




