package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.entity.BaseTrademark;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/product/baseTrademark")
@Api(tags = "品牌相关接口")
public class BaseTradeMarkController {
    @Autowired
    private BaseTrademarkService baseTrademarkService;

    /**
     * @description: 获取品牌列表
     * @author: yanhongwei
     * @date: 2023/7/11 18:17
     * @param: page
     * @param: limit
     * @return: com.atguigu.gmall.common.result.Result<com.baomidou.mybatisplus.extension.plugins.pagination.Page>
     **/
    @GetMapping("{page}/{limit}")
    @ApiOperation("分页查询品牌列表")
    public Result<Page> findTradeMarkByPage(@PathVariable("page") Integer page,@PathVariable("limit") Integer limit){
        Page page1 = baseTrademarkService.findTradeMarkByPage(page,limit);
        return  Result.build(page1, ResultCodeEnum.SUCCESS);
    }
    /**
     * @description: 添加品牌
     * @author: scv
     * @date: 2023/7/11 18:30
     * @param: baseTrademark
     * @return: com.atguigu.gmall.common.result.Result
     **/
    @PostMapping("save")
    @ApiOperation("添加品牌")
    public Result save(@RequestBody BaseTrademark baseTrademark){
        baseTrademarkService.save(baseTrademark);
        return Result.ok();
    }
    /**
     * @description: 更新品牌
     * @author: scv
     * @date: 2023/7/11 18:37
     * @param: baseTrademark
     * @return: com.atguigu.gmall.common.result.Result
     **/
    @ApiOperation("更新品牌")
    @PutMapping("update")
    public Result update(@RequestBody BaseTrademark baseTrademark){
        baseTrademarkService.updateById(baseTrademark);
        return Result.ok();
    }
    /**
     * @description: 根据id获取品牌
     * @author: scv
     * @date: 2023/7/11 18:40
     * @param: id
     * @return: com.atguigu.gmall.common.result.Result<com.atguigu.gmall.product.entity.BaseTrademark>
     **/
    @GetMapping("get/{id}")
    @ApiOperation("根据id获取品牌信息")
    public  Result<BaseTrademark> findById(@PathVariable("id") Long id){
        BaseTrademark baseTrademark = baseTrademarkService.getById(id);
        return Result.build(baseTrademark,ResultCodeEnum.SUCCESS);
    }
    /**
     * @description: 根据id删除品牌
     * @author: scv
     * @date: 2023/7/12 20:56
     * @param: id
     * @return: com.atguigu.gmall.common.result.Result
     **/
    @DeleteMapping("remove/{id}")
    @ApiOperation("根据id删除品牌")
    public Result deleteById(@PathVariable Long id){
        baseTrademarkService.deleteById(id);
        return Result.ok();
    }
    /**
     * @description: 获取品牌属性列表
     * @author: scv
     * @date: 2023/7/12 20:58
     * @return: com.atguigu.gmall.common.result.Result<java.util.List<com.atguigu.gmall.product.entity.BaseTrademark>>
     **/
    @GetMapping("getTrademarkList")
    @ApiOperation("获取品牌属性列表")
    public Result<List<BaseTrademark>> findAllTrademarkList(){
        List<BaseTrademark> baseTrademarkList = baseTrademarkService.list();
        return Result.build(baseTrademarkList,ResultCodeEnum.SUCCESS);
    }
}
