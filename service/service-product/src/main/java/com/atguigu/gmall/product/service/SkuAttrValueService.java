package com.atguigu.gmall.product.service;


import com.atguigu.gmall.product.entity.SkuAttrValue;
import com.atguigu.gmall.product.vo.AttrValueConcat;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Light of hope
* @description 针对表【sku_attr_value(sku平台属性值关联表)】的数据库操作Service
* @createDate 2023-07-10 21:27:14
*/
public interface SkuAttrValueService extends IService<SkuAttrValue> {

    public abstract List<AttrValueConcat> findAttrValueConcatBySkuId(Long skuId);
}
