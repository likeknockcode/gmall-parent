package com.atguigu.gmall.search;

import lombok.Data;


import java.util.List;

@Data
public class SearchRespAttrVo {

    private String attrName ;
    private Long attrId ;
    private List<String> attrValueList ;

}
