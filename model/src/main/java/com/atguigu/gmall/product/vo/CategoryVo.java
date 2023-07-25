package com.atguigu.gmall.product.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description：封装前端所需数据实体类
 * @Author: scv
 * @CreateTime: 2023-07-14  19:36
 * @Version: 1.0
 */
@Data
public class CategoryVo {
    private Long categoryId;
    private String categoryName;
    private List<CategoryVo> categoryChild;
}
