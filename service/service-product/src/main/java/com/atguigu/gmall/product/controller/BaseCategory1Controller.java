package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.entity.BaseCategory1;
import com.atguigu.gmall.product.service.BaseCategory1Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/admin/product")
@Api(tags = "一级分类操作接口")
public class BaseCategory1Controller {
    @Autowired
    private BaseCategory1Service baseCategory1Service;
    /**
     * @description: 获取一级列表
     * @author: scv
     * @date: 2023/7/11 18:10
     * @param: null
     * @return: Result<List<BaseCategory1>>
     **/
    @ApiOperation("获取一级分类列表")
    @GetMapping("getCategory1")
    public Result<List<BaseCategory1>> findByBaseCategory1(){
        List<BaseCategory1> baseCategory1List = baseCategory1Service.findByBaseCategory1();
        return Result.build(baseCategory1List, ResultCodeEnum.SUCCESS);
    }
}
