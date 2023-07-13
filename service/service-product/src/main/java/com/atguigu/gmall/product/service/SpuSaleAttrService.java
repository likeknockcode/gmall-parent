package com.atguigu.gmall.product.service;


import com.atguigu.gmall.product.entity.SpuSaleAttr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Light of hope
* @description 针对表【spu_sale_attr(spu销售属性)】的数据库操作Service
* @createDate 2023-07-10 21:27:14
*/
public interface SpuSaleAttrService extends IService<SpuSaleAttr> {

    public abstract List<SpuSaleAttr> findSaleAttrListBySpuId(Long spuId);
}
