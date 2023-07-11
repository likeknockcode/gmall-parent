package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/product/baseTrademark")
public class BaseTradeMarkController {
    @Autowired
    private BaseTrademarkService baseTrademarkService;

    /**
     * 获取品牌分页列表
     * @return
     */
    @GetMapping("{page}/{limit}")
    public Result<Page> findTradeMarkByPage(@PathVariable("page") Integer page,@PathVariable("limit") Integer limit){
        Page page1 = baseTrademarkService.findTradeMarkByPage(page,limit);
        return  Result.build(page1, ResultCodeEnum.SUCCESS);
    }
}
