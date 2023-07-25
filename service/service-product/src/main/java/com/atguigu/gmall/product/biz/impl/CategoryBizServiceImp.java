package com.atguigu.gmall.product.biz.impl;

import com.atguigu.gmall.product.biz.CategoryBizService;
import com.atguigu.gmall.product.entity.BaseCategory1;
import com.atguigu.gmall.product.mapper.BaseCategory1Mapper;
import com.atguigu.gmall.product.vo.CategoryView;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description：CategoryBizService实现类
 * @Author: scv
 * @CreateTime: 2023-07-14  22:48
 * @Version: 1.0
 */
@Service
public class CategoryBizServiceImp implements CategoryBizService {
    @Autowired
    private BaseCategory1Mapper baseCategory1Mapper;
    @Override
    public CategoryView findCategoryBySkuId(Long skuId) {
        CategoryView categoryView = baseCategory1Mapper.findCategoryBySkuId(skuId);
        return categoryView;
    }
}
