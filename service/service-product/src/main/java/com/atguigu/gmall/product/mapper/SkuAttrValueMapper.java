package com.atguigu.gmall.product.mapper;


import com.atguigu.gmall.product.entity.SkuAttrValue;
import com.atguigu.gmall.search.SearchAttr;
import com.atguigu.gmall.product.vo.AttrValueConcat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Light of hope
* @description 针对表【sku_attr_value(sku平台属性值关联表)】的数据库操作Mapper
* @createDate 2023-07-10 21:27:14
* @Entity com.atguigu.gmall.product.entity.SkuAttrValue
*/
public interface SkuAttrValueMapper extends BaseMapper<SkuAttrValue> {

    public abstract List<AttrValueConcat> findAttrValueConcatBySkuId(Long skuId);

    public abstract List<SearchAttr> getSkuAttrAndValueBySkuId(Long skuId);

}




