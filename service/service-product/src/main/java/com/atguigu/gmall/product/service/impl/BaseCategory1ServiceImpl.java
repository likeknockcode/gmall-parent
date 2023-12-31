package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.product.entity.BaseCategory1;
import com.atguigu.gmall.product.service.BaseCategory1Service;
import com.atguigu.gmall.product.mapper.BaseCategory1Mapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Light of hope
* @description 针对表【base_category1(一级分类表)】的数据库操作Service实现
* @createDate 2023-07-10 21:27:14
*/
@Service
public class BaseCategory1ServiceImpl extends ServiceImpl<BaseCategory1Mapper, BaseCategory1> implements BaseCategory1Service {

    @Override
    public List<BaseCategory1> findByBaseCategory1() {
        return this.list();
    }
}




