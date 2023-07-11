package com.atguigu.gmall.product.service;

import com.atguigu.gmall.product.entity.BaseCategory3;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Light of hope
* @description 针对表【base_category3(三级分类表)】的数据库操作Service
* @createDate 2023-07-10 21:27:14
*/
public interface BaseCategory3Service extends IService<BaseCategory3> {

    public abstract List<BaseCategory3> findByBaseCategory3(Long c3Id);

}
