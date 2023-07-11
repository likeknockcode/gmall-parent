package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.entity.BaseCategory1;
import com.atguigu.gmall.product.service.BaseCategory1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/product")
public class BaseCategory1Controller {
    @Autowired
    private BaseCategory1Service baseCategory1Service;
    @GetMapping("getCategory1")
    public Result<List<BaseCategory1>> findByBaseCategory1(){
        List<BaseCategory1> baseCategory1List = baseCategory1Service.findByBaseCategory1();
        return Result.build(baseCategory1List, ResultCodeEnum.SUCCESS);
    }
}
