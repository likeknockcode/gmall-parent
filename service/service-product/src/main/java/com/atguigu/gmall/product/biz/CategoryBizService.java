package com.atguigu.gmall.product.biz;

import com.atguigu.gmall.product.entity.BaseCategory1;
import com.atguigu.gmall.product.vo.CategoryView;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface CategoryBizService{
    public abstract CategoryView findCategoryBySkuId(Long skuId);

}
