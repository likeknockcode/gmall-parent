package com.atguigu.gmall.search.repository;

import com.atguigu.gmall.search.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Description：TODO
 * @Author: scv
 * @CreateTime: 2023-07-22  23:19
 * @Version: 1.0
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
