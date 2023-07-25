package com.atguigu.gmall.product.rpc;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.biz.CategoryBizService;
import com.atguigu.gmall.product.biz.SkuInfoBizService;
import com.atguigu.gmall.product.entity.SkuInfo;
import com.atguigu.gmall.product.entity.SpuSaleAttr;
import com.atguigu.gmall.product.entity.SpuSaleAttrValue;
import com.atguigu.gmall.product.service.SkuAttrValueService;
import com.atguigu.gmall.product.service.SpuSaleAttrService;
import com.atguigu.gmall.product.service.SpuSaleAttrValueService;
import com.atguigu.gmall.product.vo.AttrValueConcat;
import com.atguigu.gmall.product.vo.CategoryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description：远程调用查询接口
 * @Author: scv
 * @CreateTime: 2023-07-14  22:45
 * @Version: 1.0
 */
@RestController
@RequestMapping("api/inner/product")
public class SkuDetailRpcController {
        @Autowired
        private CategoryBizService categoryBizService;
        @Autowired
        private  SkuInfoBizService skuInfoBizService;
        @Autowired
        private SpuSaleAttrService spuSaleAttrService;


        @Autowired
        private SkuAttrValueService skuAttrValueService;
        @GetMapping("/category/{skuId}")
        public Result<CategoryView> findCategoryBySkuId(@PathVariable("skuId") Long skuId){
            CategoryView categoryView = categoryBizService.findCategoryBySkuId(skuId);
            return Result.build(categoryView, ResultCodeEnum.SUCCESS);
        }


        @GetMapping(value = "/skuInfo/{skuId}")
        public Result<SkuInfo> findSkuInfoAndImageBySkuId(@PathVariable("skuId") Long skuId){
            SkuInfo skuInfo = skuInfoBizService.findSkuInfoAndImageBySkuId(skuId);
            return Result.build(skuInfo, ResultCodeEnum.SUCCESS);
        }

        @GetMapping(value = "/skuBaseInfo/{skuId}")
        public Result<SkuInfo> findSkuById(@PathVariable(value = "skuId") Long skuId) {
            SkuInfo skuInfo = skuInfoBizService.getById(skuId) ;
            return Result.build(skuInfo,ResultCodeEnum.SUCCESS) ;
        }

        @GetMapping(value = "/findSpuSalAttrAndValueBySkuId/{skuId}")
        public Result<List<SpuSaleAttr>> findSpuSalAttrAndValueBySkuId(@PathVariable(value = "skuId") Long skuId) {
            List<SpuSaleAttr> spuSaleAttrList = spuSaleAttrService.findSpuSalAttrAndValueBySkuId(skuId) ;
            return Result.build(spuSaleAttrList,ResultCodeEnum.SUCCESS) ;
        }
        @GetMapping(value = "/findAttrValueConcatBySkuId/{skuId}")
        public  Result<List<AttrValueConcat>> findAttrValueConcatBySkuId(@PathVariable("skuId") Long skuId){
             List<AttrValueConcat> attrValueConcatList = skuAttrValueService.findAttrValueConcatBySkuId(skuId);
            return Result.build(attrValueConcatList,ResultCodeEnum.SUCCESS);
        }

        @GetMapping(value = "/findSkuIdList")
        public Result<List<SkuInfo>> findSkuIdList(){
            List<SkuInfo> skuInfoList = skuInfoBizService.findSkuIdList();
            return Result.build(skuInfoList,ResultCodeEnum.SUCCESS);
        }
}
