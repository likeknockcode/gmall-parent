package com.atguigu.gmall.product.service;


import com.atguigu.gmall.product.dto.SpuInfoDto;
import com.atguigu.gmall.product.entity.SpuImage;
import com.atguigu.gmall.product.entity.SpuInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Light of hope
* @description 针对表【spu_info(商品表)】的数据库操作Service
* @createDate 2023-07-10 21:27:14
*/
public interface SpuInfoService extends IService<SpuInfo> {

    public abstract Page findSpuInfoList(Integer pageNum, Integer pageSize, Long c3Id);

    public abstract void saveSpuInfo(SpuInfoDto spuInfoDto);


}
