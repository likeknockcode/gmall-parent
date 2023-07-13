package com.atguigu.gmall.product.service.impl;


import com.atguigu.gmall.product.dto.SkuInfoDto;
import com.atguigu.gmall.product.entity.SkuAttrValue;
import com.atguigu.gmall.product.entity.SkuImage;
import com.atguigu.gmall.product.entity.SkuInfo;
import com.atguigu.gmall.product.entity.SkuSaleAttrValue;
import com.atguigu.gmall.product.service.SkuAttrValueService;
import com.atguigu.gmall.product.service.SkuImageService;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.atguigu.gmall.product.mapper.SkuInfoMapper;
import com.atguigu.gmall.product.service.SkuSaleAttrValueService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Light of hope
* @description 针对表【sku_info(库存单元表)】的数据库操作Service实现
* @createDate 2023-07-10 21:27:14
*/
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {
    @Autowired
    private SkuImageService skuImageService;

    @Autowired
    private SkuAttrValueService skuAttrValueService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;
    @Override
    public Page findBySkuPage(Integer pageNum, Integer pageSize) {
        Page page = new Page<>(pageNum, pageSize);
        this.page(page);
        return page;
    }

    @Override
    public void saveSkuInfo(SkuInfoDto skuInfoDto) {
        //保存sku基本数据
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoDto,skuInfo);
        skuInfo.setIsSale(0);
        this.save(skuInfo);
        //保存图片数据
        List<SkuImage> skuImageList = skuInfoDto.getSkuImageList().stream().map(skuImage -> {
            skuImage.setSkuId(skuInfo.getId());
            return skuImage;
        }).collect(Collectors.toList());
        skuImageService.saveBatch(skuImageList);
        //保存sku平台属性数据
        List<SkuAttrValue> skuAttrValueList = skuInfoDto.getSkuAttrValueList().stream().map(skuAttrValue -> {
            skuAttrValue.setSkuId(skuInfo.getId());
            return skuAttrValue;
        }).collect(Collectors.toList());
        skuAttrValueService.saveBatch(skuAttrValueList);
        //保存sku销售属性数据
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfoDto.getSkuSaleAttrValueList().stream().map(skuSaleAttrValue -> {
            skuSaleAttrValue.setSkuId(skuInfo.getId());
            skuSaleAttrValue.setSpuId(Integer.parseInt(skuInfoDto.getSpuId().toString()));
            return skuSaleAttrValue;
        }).collect(Collectors.toList());
        skuSaleAttrValueService.saveBatch(skuSaleAttrValueList);
    }

    @Override
    public void listing(Long skuId) {

        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setIsSale(1);
        this.updateById(skuInfo);
    }

    @Override
    public void offShelf(Long skuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setIsSale(0);
        this.updateById(skuInfo);
    }
}




