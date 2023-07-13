package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.entity.SpuImage;
import com.atguigu.gmall.product.service.SpuImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description：spuImage业务功能接口
 * @Author: scv
 * @CreateTime: 2023-07-13  00:12
 * @Version: 1.0
 */
@RestController
@RequestMapping("admin/product")
@Api(tags = "spuImage业务功能接口")
public class SpuImageController {
    @Autowired
    private SpuImageService spuImageService;
    /**
     * @description: 根据spuId获取图片列表
     * @author: scv
     * @date: 2023/7/13 0:17
     * @param: spuId
     * @return: com.atguigu.gmall.common.result.Result<java.util.List<com.atguigu.gmall.product.entity.SpuImage>>
     **/
    @GetMapping("spuImageList/{spuId}")
    @ApiOperation("根据spuId获取图片列表")
    public Result<List<SpuImage>> findSkuImageListBySpuId(@PathVariable("spuId") Long spuId){
        List<SpuImage>  spuImageList = spuImageService.findSkuImageListBySpuId(spuId);
        return Result.build(spuImageList, ResultCodeEnum.SUCCESS);
    }
}
