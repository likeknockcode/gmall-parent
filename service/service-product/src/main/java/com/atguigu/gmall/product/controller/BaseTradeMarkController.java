package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.entity.BaseTrademark;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/admin/product/baseTrademark")
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
    public  Result<BaseTrademark> findById(@PathVariable("id") Long id){
        BaseTrademark baseTrademark = baseTrademarkService.getById(id);
        return Result.build(baseTrademark,ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("remove/{id}")
    public Result deleteById(@PathVariable Long id){

        baseTrademarkService.deleteById(id);
        return Result.ok();
    }
}
