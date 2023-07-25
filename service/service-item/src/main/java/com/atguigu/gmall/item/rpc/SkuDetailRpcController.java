package com.atguigu.gmall.item.rpc;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.item.biz.SkuDetailRpcService;
import com.atguigu.gmall.product.vo.SkuDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description：远程接口Controller
 * @Author: scv
 * @CreateTime: 2023-07-14  21:29
 * @Version: 1.0
 */
@RestController
@RequestMapping("api/inner/item")
public class SkuDetailRpcController {
    @Autowired
    private SkuDetailRpcService skuDetailRpcService;
    @GetMapping("/detail/{skuId}")
    public Result<SkuDetailVo> skuDetailVo(@PathVariable("skuId") Long skuId){

        SkuDetailVo skuDetailVo = skuDetailRpcService.skuDetailVo(skuId);
        skuDetailRpcService.updateHotScore(skuId);
        return Result.build(skuDetailVo, ResultCodeEnum.SUCCESS);
    }

}
