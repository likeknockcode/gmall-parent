package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.entity.BaseSaleAttr;
import com.atguigu.gmall.product.service.BaseSaleAttrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description：销售属性接口
 * @Author: scv
 * @CreateTime: 2023-07-12  20:51
 * @Version: 1.0
 */
@RestController
@RequestMapping("/admin/product")
@Api(tags = "销售属性管理接口")
public class BaseSaleAttrController {
    @Autowired
    private BaseSaleAttrService baseSaleAttrService;
    /**
     * @description: 销售属性列表
     * @author: scv
     * @date: 2023/7/12 20:54
     * @return: com.atguigu.gmall.common.result.Result<java.util.List<com.atguigu.gmall.product.entity.BaseSaleAttr>>
     **/
    @GetMapping("baseSaleAttrList")
    @ApiOperation("销售属性列表")
    public Result<List<BaseSaleAttr>> findAllSaleAttr(){
        List<BaseSaleAttr> baseSaleAttrList = baseSaleAttrService.list();
        return Result.build(baseSaleAttrList, ResultCodeEnum.SUCCESS);
    }
}
