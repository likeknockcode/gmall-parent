package com.atguigu.gmall.product.service.impl;


import com.atguigu.gmall.product.entity.BaseTrademark;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.atguigu.gmall.product.mapper.BaseTrademarkMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author Light of hope
* @description 针对表【base_trademark(品牌表)】的数据库操作Service实现
* @createDate 2023-07-10 21:27:14
*/
@Service
public class BaseTrademarkServiceImpl extends ServiceImpl<BaseTrademarkMapper, BaseTrademark>
    implements BaseTrademarkService{

    @Override
    public Page findTradeMarkByPage(Integer page, Integer limit) {
        Page page1 = new Page(page,limit);
        this.page(page1);
        return page1;
    }
}




