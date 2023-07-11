package com.atguigu.gmall.product.service;


import com.atguigu.gmall.product.entity.BaseTrademark;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Light of hope
* @description 针对表【base_trademark(品牌表)】的数据库操作Service
* @createDate 2023-07-10 21:27:14
*/
public interface BaseTrademarkService extends IService<BaseTrademark> {

    public abstract Page findTradeMarkByPage(Integer page, Integer limit);
}
