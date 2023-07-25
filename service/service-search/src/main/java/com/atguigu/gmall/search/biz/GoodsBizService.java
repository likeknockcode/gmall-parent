package com.atguigu.gmall.search.biz;

import com.atguigu.gmall.search.Goods;
import com.atguigu.gmall.search.SearchParamDTO;
import com.atguigu.gmall.search.SearchResponseVo;

public interface GoodsBizService {

    public abstract void save(Goods goods);

    public abstract void deleteById(Long skuId);

    public abstract SearchResponseVo search(SearchParamDTO searchParamDTO);

    public abstract void updateHotScore(Long goodsId, Integer hotScore);
}
