package com.atguigu.gmall.search.rpc;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.search.Goods;
import com.atguigu.gmall.search.SearchParamDTO;
import com.atguigu.gmall.search.SearchResponseVo;
import com.atguigu.gmall.search.biz.GoodsBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description：保存数据接口
 * @Author: scv
 * @CreateTime: 2023-07-22  23:15
 * @Version: 1.0
 */
@RestController
@RequestMapping("/api/inner/search")
public class GoodsRpcController {


    @Autowired
    private GoodsBizService goodsBizService;
    @PostMapping("/saveGoods")
    public Result saveGoods(@RequestBody Goods goods){
        goodsBizService.save(goods);
        return Result.ok();
    }
    @DeleteMapping("/deleteById/{skuId}")
    public Result deleteById(@PathVariable("skuId") Long skuId){
        goodsBizService.deleteById(skuId);
        return Result.ok();
    }

    @PostMapping("/searchGoods")
    public Result<SearchResponseVo> search(@RequestBody SearchParamDTO searchParamDTO){
        SearchResponseVo searchResponseVo = goodsBizService.search(searchParamDTO);
        return Result.build(searchResponseVo, ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/updateHotScore")
    public Result updateHotScore(@RequestParam("goodsId") Long goodsId,@RequestParam("hotScore") Integer hotScore){
        goodsBizService.updateHotScore(goodsId,hotScore);
        return Result.ok();
    }
}
