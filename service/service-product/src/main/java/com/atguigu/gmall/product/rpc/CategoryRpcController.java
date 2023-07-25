package com.atguigu.gmall.product.rpc;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.product.biz.CategoryRpcService;
import com.atguigu.gmall.product.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description：远程调用接口
 * @Author: scv
 * @CreateTime: 2023-07-14  19:48
 * @Version: 1.0
 */
@RestController
@RequestMapping("api/inner/product")
public class CategoryRpcController {

    @Autowired
    private CategoryRpcService categoryRpcService;

    @GetMapping("/categorys/tree")
    public Result<List<CategoryVo>> findAllCategoryTree(){
        List<CategoryVo> categoryVoList  = categoryRpcService.findAllCategoryTree();
        return Result.build(categoryVoList, ResultCodeEnum.SUCCESS);
    }

}
