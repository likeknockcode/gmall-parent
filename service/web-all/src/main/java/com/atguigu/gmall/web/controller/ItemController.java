package com.atguigu.gmall.web.controller;

import com.atguigu.gmall.common.feign.item.SkuDetailFeignClient;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.vo.CategoryView;
import com.atguigu.gmall.product.vo.SkuDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description：跳转到item的index.html页面
 * @Author: scv
 * @CreateTime: 2023-07-14  21:25
 * @Version: 1.0
 */
@Controller
public class ItemController {

    @Autowired
    private SkuDetailFeignClient skuDetailFeignClient;
    @GetMapping(value = "/{skuId}.html")
    public String item(@PathVariable("skuId") Long skuId, Model model){
        //TODO 返回业务数据
        //三级分类数据
        Result<SkuDetailVo> skuDetailVoResult = skuDetailFeignClient.skuDetailVo(skuId);
        CategoryView categoryView = skuDetailVoResult.getData().getCategoryView();
        model.addAttribute("categoryView",categoryView);
        // 将skuInfo数据保存到model对象中
        model.addAttribute("skuInfo" , skuDetailVoResult.getData().getSkuInfo()) ;

        // 将价格数据存储到Model对象中
        model.addAttribute("price" , skuDetailVoResult.getData().getPrice()) ;

        // 将spu的销售属性存储到model对象中
        model.addAttribute("spuSaleAttrList" , skuDetailVoResult.getData().getSpuSaleAttrList()) ;

        model.addAttribute("valuesSkuJson",skuDetailVoResult.getData().getValuesSkuJson());
        return "item/index";
    }
}
