package com.atguigu.gmall.product.service.impl;


import com.atguigu.gmall.product.entity.SpuImage;
import com.atguigu.gmall.product.service.SpuImageService;
import com.atguigu.gmall.product.mapper.SpuImageMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Light of hope
* @description 针对表【spu_image(商品图片表)】的数据库操作Service实现
* @createDate 2023-07-10 21:27:14
*/
@Service
public class SpuImageServiceImpl extends ServiceImpl<SpuImageMapper, SpuImage> implements SpuImageService {

    @Override
    public List<SpuImage> findSkuImageListBySpuId(Long spuId) {
        LambdaQueryWrapper<SpuImage> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SpuImage::getSpuId,spuId);
        List<SpuImage> list = this.list(lambdaQueryWrapper);
        return list;
    }
}




