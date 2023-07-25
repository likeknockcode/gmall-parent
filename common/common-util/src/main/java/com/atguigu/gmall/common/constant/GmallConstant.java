package com.atguigu.gmall.common.constant;

import java.util.Stack;

public interface GmallConstant {
    /*
    * redis中缓存所有分类数据的key
    * */
    public static final String REDIS_CACHE_CATEGORY_KEY = "categoryList";

    /*
    * 数据库不存在，redis存储的值的value
    * */
    public static final String DB_NULL_CATEGORY_VALUE = "X";

    /*
    *
    * redis中缓存的skudetail数据的key
    * */
    public static final String REDIS_CACHE_SKU_PREFIX = "skuDetailVo";
    public static final String REDIS_ITEM_LOCK_PREFIX = "item-lock";

    public static final String REDIS_SKUID_BLOOM_FILTER = "sku-bloom-filter";
    public static final String REDIS_SKUID_BLOOM_FILTER_NEW = "sku-bloom-filter_new";
    public static final String REDIS_SKU_HOTSCORE_PRE = "redis-sku-hotscore:";
    public static final String REDIS_LOGIN_USER = "login-user:";
}
