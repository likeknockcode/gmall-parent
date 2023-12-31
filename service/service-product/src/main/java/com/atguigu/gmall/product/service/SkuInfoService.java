package com.atguigu.gmall.product.service;


import com.atguigu.gmall.product.dto.SkuInfoDto;
import com.atguigu.gmall.product.entity.SkuInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Light of hope
* @description 针对表【sku_info(库存单元表)】的数据库操作Service
* @createDate 2023-07-10 21:27:14
*/
public interface SkuInfoService extends IService<SkuInfo> {

    public  abstract Page findBySkuPage(Integer pageNum, Integer pageSize);


    public abstract void saveSkuInfo(SkuInfoDto skuInfoDto);

    public abstract void listing(Long skuId);

    public abstract void offShelf(Long skuId);
}
