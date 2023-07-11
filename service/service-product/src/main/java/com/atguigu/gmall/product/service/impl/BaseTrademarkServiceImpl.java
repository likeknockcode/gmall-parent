package com.atguigu.gmall.product.service.impl;


import com.atguigu.gmall.common.execption.GmallException;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.entity.BaseTrademark;
import com.atguigu.gmall.product.entity.SkuInfo;
import com.atguigu.gmall.product.entity.SpuInfo;
import com.atguigu.gmall.product.mapper.SkuInfoMapper;
import com.atguigu.gmall.product.mapper.SpuInfoMapper;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.atguigu.gmall.product.mapper.BaseTrademarkMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Light of hope
* @description 针对表【base_trademark(品牌表)】的数据库操作Service实现
* @createDate 2023-07-10 21:27:14
*/
@Service
public class BaseTrademarkServiceImpl extends ServiceImpl<BaseTrademarkMapper, BaseTrademark> implements BaseTrademarkService {
    @Autowired
    private  SpuInfoMapper spuInfoMapper;
    @Autowired
    private SkuInfoMapper skuInfoMapper;
    @Override
    public Page findTradeMarkByPage(Integer page, Integer limit) {
        Page page1 = new Page(page,limit);
        this.page(page1);
        return page1;
    }

    @Override
    public void deleteById(Long id) {
        //查询spu列表
        LambdaQueryWrapper<SpuInfo> spuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        spuLambdaQueryWrapper.eq(SpuInfo::getTmId,id);
        List<SpuInfo> spuInfoList = spuInfoMapper.selectList(spuLambdaQueryWrapper);
        if (spuInfoList != null && spuInfoList.size() > 0){
            throw new GmallException(ResultCodeEnum.SPU_ERROR);
        }

        //查询sku列表
        LambdaQueryWrapper<SkuInfo> skuInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        skuInfoLambdaQueryWrapper.eq(SkuInfo::getTmId,id);
        List<SkuInfo> skuInfoList = skuInfoMapper.selectList(skuInfoLambdaQueryWrapper);
        if (skuInfoList != null && skuInfoList.size() > 0){
            throw new GmallException(ResultCodeEnum.SKU_ERROR);
        }
        //根据id删除
        removeById(id);
    }
}




