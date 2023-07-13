package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.entity.BaseAttrValue;
import com.atguigu.gmall.product.service.BaseAttrValueService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description：平台属性值管理接口
 * @Author: scv
 * @CreateTime: 2023-07-12  18:59
 * @Version: 1.0
 */
@RestController
@RequestMapping("/admin/product")
@Api(tags = "平台属性值管理接口")
public class BaseAttrValueController {
    @Autowired
    private BaseAttrValueService baseAttrValueService;

    @GetMapping("getAttrValueList/{attrId}")
    public Result<List<BaseAttrValue>> getAttrValueList(@PathVariable("attrId") Long attrId){
        LambdaQueryWrapper<BaseAttrValue> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseAttrValue::getAttrId,attrId);
        List<BaseAttrValue> baseAttrValueList = baseAttrValueService.list(lambdaQueryWrapper);
        return Result.build(baseAttrValueList, ResultCodeEnum.SUCCESS);
    }
}
