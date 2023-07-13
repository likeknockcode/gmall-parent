package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.entity.SpuSaleAttr;
import com.atguigu.gmall.product.service.SpuSaleAttrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description：spu销售属性业务接口
 * @Author: scv
 * @CreateTime: 2023-07-13  00:16
 * @Version: 1.0
 */
@RestController
@RequestMapping("/admin/product")
@Api(tags = "spu销售属性业务接口")
public class SpuSaleAttrController {
    @Autowired
    private SpuSaleAttrService spuSaleAttrService;
    /**
     * @description: 根据spuId获取销售属性列表
     * @author: scv
     * @date: 2023/7/13 0:23
     * @param: spuId
     * @return: com.atguigu.gmall.common.result.Result<java.util.List<com.atguigu.gmall.product.entity.SpuSaleAttr>>
     **/
    @GetMapping("spuSaleAttrList/{spuId}")
    @ApiOperation("根据spuId获取销售属性列表")
    public Result<List<SpuSaleAttr>> findSaleAttrListBySpuId(@PathVariable("spuId") Long spuId){
        List<SpuSaleAttr> spuSaleAttrList = spuSaleAttrService.findSaleAttrListBySpuId(spuId);
        return  Result.build(spuSaleAttrList, ResultCodeEnum.SUCCESS);
    }
}
