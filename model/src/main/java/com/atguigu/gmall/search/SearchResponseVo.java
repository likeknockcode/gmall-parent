package com.atguigu.gmall.search;


import lombok.Data;

import java.util.List;

@Data
public class SearchResponseVo {

    private SearchParamDTO searchParam ;

    // 品牌面包屑
    private String trademarkParam ;

    // url参数
    private String urlParam ;

    // 平台属性面包屑
    private List<SearchAttr> propsParamList ;

    // 品牌列表
    private List<SearchTmVo> trademarkList ;

    // 属性列表
    private List<SearchRespAttrVo> attrsList ;

    // 排序信息
    private SearchOrderMapVo orderMap ;

    // 商品列表
    private List<Goods> goodsList ;

    // 当前页面
    private Integer pageNo ;

    // 总页码
    private Long totalPages ;
}
