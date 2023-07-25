package com.atguigu.gmall.product.service.impl;


import com.atguigu.gmall.common.feign.search.GoodsFeignClient;
import com.atguigu.gmall.product.dto.SkuInfoDto;
import com.atguigu.gmall.product.entity.*;
import com.atguigu.gmall.product.mapper.BaseCategory1Mapper;
import com.atguigu.gmall.product.mapper.BaseTrademarkMapper;
import com.atguigu.gmall.product.mapper.SkuAttrValueMapper;
import com.atguigu.gmall.search.Goods;
import com.atguigu.gmall.search.SearchAttr;
import com.atguigu.gmall.product.service.SkuAttrValueService;
import com.atguigu.gmall.product.service.SkuImageService;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.atguigu.gmall.product.mapper.SkuInfoMapper;
import com.atguigu.gmall.product.service.SkuSaleAttrValueService;
import com.atguigu.gmall.product.vo.CategoryView;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
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

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private BaseTrademarkMapper baseTrademarkMapper;
    @Autowired
    private BaseCategory1Mapper baseCategory1Mapper;
    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;
    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private GoodsFeignClient goodsFeignClient;

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

        Goods goods = buildGoods(skuId);
        goodsFeignClient.saveGoods(goods);
    }

    @Override
    public void offShelf(Long skuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setIsSale(0);
        this.updateById(skuInfo);
        goodsFeignClient.deleteById(skuId);
    }

    //构建Es数据模型对象
    public Goods buildGoods(Long skuId){
        //创建goods对象
        Goods goods = new Goods();

        //查询sku对象
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);

        //封装数据
        CompletableFuture<Void> baseInfoCf = CompletableFuture.runAsync(() -> {
            goods.setId(skuId);
            goods.setDefaultImg(skuInfo.getSkuDefaultImg());
            goods.setTitle(skuInfo.getSkuName());
            goods.setPrice(new BigDecimal(skuInfo.getPrice()));
            goods.setCreateTime(new Date());
        }, threadPoolExecutor);

        //封装品牌数据
        CompletableFuture<Void> trademarkCf = CompletableFuture.runAsync(() -> {
            BaseTrademark baseTrademark = baseTrademarkMapper.selectById(skuInfo.getTmId());
            goods.setTmId(baseTrademark.getId());
            goods.setTmName(baseTrademark.getTmName());
            goods.setTmLogoUrl(baseTrademark.getLogoUrl());
        }, threadPoolExecutor);

        //三级分类数据
        CompletableFuture<Void> categoryViewCf = CompletableFuture.runAsync(() -> {
            CategoryView categoryView = baseCategory1Mapper.findCategoryBySkuId(skuId);
            goods.setCategory1Id(categoryView.getCategory1Id());
            goods.setCategory1Name(categoryView.getCategory1Name());
            goods.setCategory2Id(categoryView.getCategory2Id());
            goods.setCategory2Name(categoryView.getCategory2Name());
            goods.setCategory3Id(categoryView.getCategory3Id());
            goods.setCategory3Name(categoryView.getCategory3Name());
        }, threadPoolExecutor);
        //设置热点分数
        goods.setHotScore(0L);

        //设置平台属性

        CompletableFuture<Void> searchAttrCf = CompletableFuture.runAsync(() -> {
            List<SearchAttr> searchAttrs = skuAttrValueMapper.getSkuAttrAndValueBySkuId(skuId);
            goods.setAttrs(searchAttrs);
        }, threadPoolExecutor);

        //等到所有线程执行完毕
        CompletableFuture.allOf(baseInfoCf,trademarkCf,categoryViewCf,searchAttrCf).join();
        return goods;

    }
}




