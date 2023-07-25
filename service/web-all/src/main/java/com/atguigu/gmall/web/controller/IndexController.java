package com.atguigu.gmall.web.controller;

import com.atguigu.gmall.common.feign.product.BaseCategoryFeignClient;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Description：web-allController类
 * @Author: scv
 * @CreateTime: 2023-07-14  19:04
 * @Version: 1.0
 */
@Controller
public class IndexController {
    @Autowired
    private BaseCategoryFeignClient baseCategoryFeignClient;
    /**
     * @description: 访问首页
     * @author: scv
     * @date: 2023/7/14 19:05
     * @return: java.lang.String
     **/
    @GetMapping(value = {"/","/index.html"})
    public String index(Model model){

        Result<List<CategoryVo>> allCategoryTree = baseCategoryFeignClient.findAllCategoryTree();
        List<CategoryVo> data = allCategoryTree.getData();
        model.addAttribute("list",data);
        return "index/index";
    }

}
