package com.atguigu.gmall.product.biz.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.common.cache.anno.GmallCache;
import com.atguigu.gmall.common.constant.GmallConstant;
import com.atguigu.gmall.product.biz.CategoryRpcService;
import com.atguigu.gmall.product.mapper.BaseCategory1Mapper;
import com.atguigu.gmall.product.vo.CategoryVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Description：CategoryRpcService实现类
 * @Author: scv
 * @CreateTime: 2023-07-14  19:49
 * @Version: 1.0
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryRpcService {
    @Autowired
    private BaseCategory1Mapper baseCategory1Mapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //定义一个换从容器
    private static final ConcurrentHashMap<String,List<CategoryVo>> CACHE_MAP = new ConcurrentHashMap<>();

    public List<CategoryVo> findAllCategoryTreeReids() {
        /* 本地缓存
        //从缓存中进行命中，如果命中直接返回

        List<CategoryVo> categoryVoList1 = CACHE_MAP.get("categoryList");
        if (categoryVoList1 != null){
            log.info("从缓存中命中三级分类数据----------------------");
            return categoryVoList1;
        }
        List<CategoryVo> categoryVoList = baseCategory1Mapper.findAllCategoryTree();
        log.info("从数据库中查询三级分类数据-------------------");
        //将数据加入到本地缓存中
        CACHE_MAP.put("categoryList",categoryVoList);*/

        /*redis分布式缓存
        * */
        //从redis缓存中进行命中，如果命中直接返回
        String categoryVoListJSON = redisTemplate.opsForValue().get(GmallConstant.REDIS_CACHE_CATEGORY_KEY);
        if (!StringUtils.isEmpty(categoryVoListJSON)){
            log.info("从redis中命中缓存，直接返回--------------------");
            if (GmallConstant.DB_NULL_CATEGORY_VALUE.equalsIgnoreCase(categoryVoListJSON)){
                log.info("数据库中不存在此数据,从缓存中查到了X----------------");
                return null;
            }
            //返回正常数据
            return JSON.parseArray(categoryVoListJSON, CategoryVo.class);
        }

        //缓存中不存在查询数据库数据
        List<CategoryVo> categoryVoList = baseCategory1Mapper.findAllCategoryTree();
        log.info("从数据库中查询三级分类数据-----------------");
        if (categoryVoList != null && categoryVoList.size() > 0){
            //将数据加入到缓存中
            log.info("将数据库查询到的数据加入到缓存中-----------------");
            redisTemplate.opsForValue().set(GmallConstant.REDIS_CACHE_CATEGORY_KEY,JSON.toJSONString(categoryVoList),30, TimeUnit.SECONDS);
        }else {
            redisTemplate.opsForValue().set(GmallConstant.REDIS_CACHE_CATEGORY_KEY,GmallConstant.DB_NULL_CATEGORY_VALUE);
        }
        return categoryVoList;
    }

    @Override
    @GmallCache(cacheKey = "allCategory")
    public List<CategoryVo> findAllCategoryTree() {
        List<CategoryVo> categoryVoList = baseCategory1Mapper.findAllCategoryTree();
        return categoryVoList;
    }
}
