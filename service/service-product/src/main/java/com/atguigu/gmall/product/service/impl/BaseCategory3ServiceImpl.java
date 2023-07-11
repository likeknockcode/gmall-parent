package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.product.entity.BaseCategory3;
import com.atguigu.gmall.product.service.BaseCategory3Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gmall.product.mapper.BaseCategory3Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Light of hope
* @description 针对表【base_category3(三级分类表)】的数据库操作Service实现
* @createDate 2023-07-10 21:27:14
*/
@Service
public class BaseCategory3ServiceImpl extends ServiceImpl<BaseCategory3Mapper, BaseCategory3>
    implements BaseCategory3Service {

    @Override
    public List<BaseCategory3> findByBaseCategory3(Long c2Id) {
        LambdaQueryWrapper<BaseCategory3> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseCategory3::getCategory2Id,c2Id);

        return this.list(lambdaQueryWrapper);
    }
}




