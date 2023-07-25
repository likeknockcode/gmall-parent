package com.atguigu.gmall.product.service.impl;


import com.atguigu.gmall.common.constant.GmallConstant;
import com.atguigu.gmall.product.dto.SpuInfoDto;
import com.atguigu.gmall.product.entity.SpuImage;
import com.atguigu.gmall.product.entity.SpuInfo;
import com.atguigu.gmall.product.entity.SpuSaleAttr;
import com.atguigu.gmall.product.entity.SpuSaleAttrValue;
import com.atguigu.gmall.product.service.SpuImageService;
import com.atguigu.gmall.product.service.SpuInfoService;
import com.atguigu.gmall.product.mapper.SpuInfoMapper;
import com.atguigu.gmall.product.service.SpuSaleAttrService;
import com.atguigu.gmall.product.service.SpuSaleAttrValueService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Light of hope
* @description 针对表【spu_info(商品表)】的数据库操作Service实现
* @createDate 2023-07-10 21:27:14
*/
@Service
@Slf4j
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfo> implements SpuInfoService {
    @Autowired
    private SpuImageService spuImageService;

    @Autowired
    private SpuSaleAttrService spuSaleAttrService;
    @Autowired
    private SpuSaleAttrValueService spuSaleAttrValueService;


    @Override
    public Page findSpuInfoList(Integer pageNum, Integer pageSize, Long c3Id) {
        Page page = new Page(pageNum,pageSize);
        LambdaQueryWrapper<SpuInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SpuInfo::getCategory3Id,c3Id);
        this.page(page,lambdaQueryWrapper);
        return page;
    }

    @Override
    public void saveSpuInfo(SpuInfoDto spuInfoDto) {
        //添加spuInfo基本数据
        SpuInfo spuInfo = new SpuInfo();
        BeanUtils.copyProperties(spuInfoDto,spuInfo);
        this.save(spuInfo);
        //添加spuInfo图片数据
        List<SpuImage> spuImageList = spuInfoDto.getSpuImageList().stream().map(spuImage -> {
            spuImage.setSpuId(spuInfo.getId());
            return spuImage;
        }).collect(Collectors.toList());
        spuImageService.saveBatch(spuImageList);
        //添加spuInfo销售属性数据
        List<SpuSaleAttr> spuSaleAttrList = spuInfoDto.getSpuSaleAttrList().stream().map(spuSaleAttr -> {
            spuSaleAttr.setSpuId(spuInfo.getId());
            return spuSaleAttr;
        }).collect(Collectors.toList());
        spuSaleAttrService.saveBatch(spuSaleAttrList);
        //添加spuInfo销售属性值数据
        spuInfoDto.getSpuSaleAttrList().forEach(spuSaleAttr -> {
            List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList().stream().map(spuSaleAttrValue -> {
                spuSaleAttrValue.setSpuId(spuInfo.getId());
                spuSaleAttrValue.setSaleAttrName(spuSaleAttr.getSaleAttrName());
                return spuSaleAttrValue;
            }).collect(Collectors.toList());
            spuSaleAttrValueService.saveBatch(spuSaleAttrValueList);
        });
    }


}




