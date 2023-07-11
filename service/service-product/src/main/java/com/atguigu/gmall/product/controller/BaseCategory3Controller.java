package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.entity.BaseCategory3;
import com.atguigu.gmall.product.service.BaseCategory3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/product")
public class BaseCategory3Controller {
    @Autowired
    private BaseCategory3Service baseCategory3Service;

    @GetMapping("getCategory3/{category2Id}")
    public Result<List<BaseCategory3>> findByBaseCategory3(@PathVariable("category2Id") Long c3Id){
        List<BaseCategory3> baseCategory3List = baseCategory3Service.findByBaseCategory3(c3Id);
        return Result.build(baseCategory3List, ResultCodeEnum.SUCCESS);
    }
}
