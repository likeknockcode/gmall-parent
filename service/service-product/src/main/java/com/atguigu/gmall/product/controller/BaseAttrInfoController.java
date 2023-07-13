package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.entity.BaseAttrInfo;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description：平台属性管理接口
 * @Author: scv
 * @CreateTime: 2023-07-12  10:57
 * @Version: 1.0
 */
@RestController
@RequestMapping("/admin/product")
@Api(tags = "平台属性管理接口")
public class BaseAttrInfoController {
    @Autowired
    BaseAttrInfoService baseAttrInfoService;
    /**
     * @description: 根据分类id获取平台属性
     * @author: scv
     * @date: 2023/7/12 18:45
     * @param: c1Id
     * @param: c2Id
     * @param: c3Id
     * @return: com.atguigu.gmall.common.result.Result<java.util.List<com.atguigu.gmall.product.entity.BaseAttrInfo>>
     **/
    @GetMapping("attrInfoList/{c1Id}/{c2Id}/{c3Id}")
    @ApiOperation("根据分类id获取平台属性")
    public Result<List<BaseAttrInfo>> findAttrInfoList(@PathVariable("c1Id") Long c1Id,
                                                       @PathVariable("c2Id") Long c2Id,
                                                       @PathVariable("c3Id") Long c3Id){
        List<BaseAttrInfo> baseAttrInfoList = baseAttrInfoService.findAttrInfoList(c1Id,c2Id,c3Id);
        return  Result.build(baseAttrInfoList, ResultCodeEnum.SUCCESS);
    }
    /**
     * @description: 添加平台属性
     * @author: scv
     * @date: 2023/7/12 18:54
     * @param: baseAttrInfo
     * @return: com.atguigu.gmall.common.result.Result
     **/
    @PostMapping("saveAttrInfo")
    @ApiOperation("添加平台属性")
    public Result saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        baseAttrInfoService.saveAttrInfo(baseAttrInfo);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

}
