package com.atguigu.gmall.web.controller;

import com.atguigu.gmall.common.feign.search.GoodsFeignClient;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.search.SearchParamDTO;
import com.atguigu.gmall.search.SearchResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @Description：页面跳转到搜索列表界面
 * @Author: scv
 * @CreateTime: 2023-07-22  16:39
 * @Version: 1.0
 */
@Controller
public class SearchController {
    @Autowired
    private GoodsFeignClient goodsFeignClient;
    @GetMapping(value = "/list.html")
    public String searchListPage(SearchParamDTO searchParamDTO, Model model){

        Result<SearchResponseVo> search = goodsFeignClient.search(searchParamDTO);

        SearchResponseVo searchResponseVo = search.getData();

        model.addAttribute("searchParam" , searchResponseVo.getSearchParam()) ;
        model.addAttribute("trademarkParam" , searchResponseVo.getTrademarkParam()) ;
        model.addAttribute("urlParam" , searchResponseVo.getUrlParam()) ;
        model.addAttribute("propsParamList" , searchResponseVo.getPropsParamList()) ;
        model.addAttribute("trademarkList" , searchResponseVo.getTrademarkList()) ;
        model.addAttribute("attrsList" , searchResponseVo.getAttrsList()) ;
        model.addAttribute("orderMap" , searchResponseVo.getOrderMap()) ;
        model.addAttribute("goodsList" , searchResponseVo.getGoodsList()) ;
        model.addAttribute("pageNo" , searchResponseVo.getPageNo()) ;
        model.addAttribute("totalPages" , searchResponseVo.getTotalPages()) ;
        return "list/index";
    }
}
