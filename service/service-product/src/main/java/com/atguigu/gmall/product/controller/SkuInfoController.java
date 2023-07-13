package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.dto.SkuInfoDto;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * @Description：Sku业务功能接口
 * @Author: scv
 * @CreateTime: 2023-07-12  23:50
 * @Version: 1.0
 */
@RestController
@RequestMapping("/admin/product")
@Api(tags = "Sku业务功能接口")
public class SkuInfoController {
    @Autowired
    private SkuInfoService skuInfoService;
    /**
     * @description: 获取sku分列表
     * @author: scv
     * @date: 2023/7/12 23:59
     * @param: pageNum
     * @param: pageSize
     * @return: com.atguigu.gmall.common.result.Result<com.baomidou.mybatisplus.extension.plugins.pagination.Page>
     **/
    @GetMapping("list/{page}/{limit}")
    @ApiOperation("获取sku分页列表")
    public Result<Page> findBySkuPage(@PathVariable("page") Integer pageNum ,
                                      @PathVariable("limit") Integer pageSize){
        Page page = skuInfoService.findBySkuPage(pageNum,pageSize);
        return Result.build(page, ResultCodeEnum.SUCCESS);
    }
    /**
     * @description: 添加Sku
     * @author: scv
     * @date: 2023/7/13 1:08
     * @param: skuInfoDto
     * @return: com.atguigu.gmall.common.result.Result
     **/
    @PostMapping("saveSkuInfo")
    @ApiOperation("添加Sku")
    public Result saveSkuInfo(@RequestBody SkuInfoDto skuInfoDto){
        skuInfoService.saveSkuInfo(skuInfoDto);
        return Result.ok();
    }
    /**
     * @description: sku的上架
     * @author: scv
     * @date: 2023/7/13 9:57
     * @param: skuId
     * @return: com.atguigu.gmall.common.result.Result
     **/
    @GetMapping("onSale/{skuId}")
    @ApiOperation("sku的上架")
    public Result listing(@PathVariable("skuId") Long skuId){
        skuInfoService.listing(skuId);
        return Result.ok();
    }
    /**
     * @description: sku的下架
     * @author: scv
     * @date: 2023/7/13 10:01
     * @param: skuId
     * @return: com.atguigu.gmall.common.result.Result
     **/
    @GetMapping("cancelSale/{skuId}")
    @ApiOperation("sku的下架")
    public Result offShelf(@PathVariable("skuId") Long skuId){
        skuInfoService.offShelf(skuId);
        return Result.ok();
    }
}
























