package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.dto.SpuInfoDto;
import com.atguigu.gmall.product.entity.BaseSaleAttr;
import com.atguigu.gmall.product.entity.SpuImage;
import com.atguigu.gmall.product.service.SpuInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description：Spu业务功能接口接口
 * @Author: scv
 * @CreateTime: 2023-07-12  20:20
 * @Version: 1.0
 */
@RestController
@RequestMapping("/admin/product")
@Api(tags = "Spu业务功能接口接口")
public class SpuInfoController {
    @Autowired
    private  SpuInfoService spuInfoService;

    /**
     * @description: 获取Spu分页列表
     * @author: scv
     * @date: 2023/7/12 20:46
     * @param: pageNum
     * @param: pageSize
     * @param: c3Id
     * @return: com.atguigu.gmall.common.result.Result<com.baomidou.mybatisplus.extension.plugins.pagination.Page>
     **/
    @GetMapping("{page}/{limit}")
    @ApiOperation("获取spu分页列表")
    public Result<Page> findSpuInfoList(@PathVariable("page") Integer pageNum,
                                        @PathVariable("limit") Integer pageSize,
                                        @RequestParam("category3Id") Long c3Id){
        Page page = spuInfoService.findSpuInfoList(pageNum,pageSize,c3Id);
        return  Result.build(page, ResultCodeEnum.SUCCESS);
    }
    /**
     * @description: 添加Spu
     * @author: scv
     * @date: 2023/7/12 21:23
     * @param: spuInfoDto
     * @return: com.atguigu.gmall.common.result.Result
     **/
    @PostMapping("saveSpuInfo")
    @ApiOperation("添加Spu")
    public Result saveSpuInfo(@RequestBody SpuInfoDto spuInfoDto){
        spuInfoService.saveSpuInfo(spuInfoDto);
        return Result.ok();
    }


}
