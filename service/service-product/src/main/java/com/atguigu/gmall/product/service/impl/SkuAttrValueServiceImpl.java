package com.atguigu.gmall.product.service.impl;


import com.atguigu.gmall.product.entity.SkuAttrValue;
import com.atguigu.gmall.product.service.SkuAttrValueService;
import com.atguigu.gmall.product.mapper.SkuAttrValueMapper;
import com.atguigu.gmall.product.vo.AttrValueConcat;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Light of hope
* @description 针对表【sku_attr_value(sku平台属性值关联表)】的数据库操作Service实现
* @createDate 2023-07-10 21:27:14
*/
@Service
public class SkuAttrValueServiceImpl extends ServiceImpl<SkuAttrValueMapper, SkuAttrValue> implements SkuAttrValueService {
    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;
    @Override
    public List<AttrValueConcat> findAttrValueConcatBySkuId(Long skuId) {
        List<AttrValueConcat> attrValueConcatList = skuAttrValueMapper.findAttrValueConcatBySkuId(skuId);
        return attrValueConcatList;
    }
}




