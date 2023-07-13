package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.entity.BaseCategory3;
import com.atguigu.gmall.product.service.BaseCategory3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/product")
@Api(tags = "三级分类接口")
public class BaseCategory3Controller {
    @Autowired
    private BaseCategory3Service baseCategory3Service;
    /**
     * @description: 获取三级列表
     * @author: scv
     * @date: 2023/7/11 18:11
     * @param: c2Id
     * @return: Result<List<BaseCategory3>>
     **/
    @GetMapping("getCategory3/{category2Id}")
    @ApiOperation("根据二级Id查询三级分类列表")
    public Result<List<BaseCategory3>> findByBaseCategory3(@PathVariable("category2Id") Long c2Id){
        List<BaseCategory3> baseCategory3List = baseCategory3Service.findByBaseCategory3(c2Id);
        return Result.build(baseCategory3List, ResultCodeEnum.SUCCESS);
    }
}
