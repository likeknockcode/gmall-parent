package com.atguigu.gmall.item.biz.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.common.cache.anno.GmallCache;
import com.atguigu.gmall.common.cache.service.RedisCacheService;
import com.atguigu.gmall.common.constant.GmallConstant;
import com.atguigu.gmall.common.feign.product.SkuDetailFeignClient;
import com.atguigu.gmall.common.feign.search.GoodsFeignClient;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.item.biz.SkuDetailRpcService;
import com.atguigu.gmall.product.vo.AttrValueConcat;
import com.atguigu.gmall.product.vo.CategoryView;
import com.atguigu.gmall.product.vo.SkuDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Description：service实现类
 * @Author: scv
 * @CreateTime: 2023-07-14  21:30
 * @Version: 1.0
 */
@Service
@Slf4j
public class SkuDetailRpcServiceImpl implements SkuDetailRpcService{
    /*
    *
    * 初始化布隆过滤器
    * */
//    public static final BloomFilter BLOOM_FILTER = BloomFilter.create(Funnels.longFunnel(),1000000,0.00001);
    @Autowired
    private SkuDetailFeignClient skuDetailFeignClient;
    @Autowired
    private RedisCacheService redisCacheService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private GoodsFeignClient goodsFeignClient;


    //@PostConstruct //对象初始化阶段，最对象创建后，依赖注入完成后立即执行该方法
    /*public void init(){
        Result<List<SkuInfo>> skuIdList = skuDetailFeignClient.findSkuIdList();
        skuIdList.getData().stream().forEach(skuInfo -> {BLOOM_FILTER.put(skuInfo.getId());});
    }*/
    /*@Override
    public SkuDetailVo skuDetailVo(Long skuId) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //查询布隆过滤器
        if (!BLOOM_FILTER.mightContain(skuId)){
            log.info("布隆过滤器中没有该值，直接返回null-----------");
            return null;
        }

        String redisCacheSkuInfo = redisTemplate.opsForValue().get(GmallConstant.REDIS_CACHE_SKU_PREFIX+skuId);
        if (!StringUtils.isEmpty(redisCacheSkuInfo)){
            if (GmallConstant.DB_NULL_CATEGORY_VALUE.equalsIgnoreCase(redisCacheSkuInfo)){
                log.info("redis中缓存了数据库中不存在的数据--------------");
                return null;
            }else {
                log.info("{}线程在redis中命中缓存",Thread.currentThread().getId());
                return JSON.parseObject(redisCacheSkuInfo,SkuDetailVo.class);
            }
        }else{
            boolean lock = lock(uuid);
            if (lock){
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                SkuDetailVo skuDetailVo = null;
                try {
                    //从数据库查询，加入到redis缓存中
                    skuDetailVo = this.encapsulation(skuId);
                    if (skuDetailVo == null){
                        log.info("{}线程查询数据库不存在此数据，redis中缓存X---------------",Thread.currentThread().getId());
                        redisTemplate.opsForValue().set(GmallConstant.REDIS_CACHE_SKU_PREFIX+skuId,GmallConstant.DB_NULL_CATEGORY_VALUE);
                    }else {
                        log.info("{}线程查询数据库存在此数据，redis中缓存skuDetailVo---------------",Thread.currentThread().getId());
                        redisTemplate.opsForValue().set(GmallConstant.REDIS_CACHE_SKU_PREFIX+skuId,JSON.toJSONString(skuDetailVo),30,TimeUnit.SECONDS);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    unlock(uuid);
                }
                return skuDetailVo;
            }else {
                while (redisCacheSkuInfo == null) {
                    log.info("{}线程缓存未命中",Thread.currentThread().getId());
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    redisCacheSkuInfo = redisTemplate.opsForValue().get(GmallConstant.REDIS_CACHE_SKU_PREFIX + skuId);
                }
                log.info("{}线程从redis中命中缓存--------",Thread.currentThread().getId());
                return JSON.parseObject(redisCacheSkuInfo,SkuDetailVo.class);
            }
        }
    }*/
    public void unlock(String uuid){
        //lua脚本
        String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1]\n" +
                "then\n" +
                "    return redis.call(\"del\",KEYS[1])\n" +
                "else\n" +
                "    return 0\n" +
                "end";
        Long result = redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Arrays.asList("lock"), uuid);
        if (result == 1){
            System.out.println("锁释放成功！");
        }else {
            System.out.println("释放锁失败，锁是别人的！！");
        }
    }
    /**
     * @description: 加锁 redis分布式锁
     * @author: scv
     * @date: 2023/7/17 23:08
     * @param: uuid
     **/
    public boolean lock(String uuid){
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 30, TimeUnit.SECONDS);
        return lock;
    }
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    public SkuDetailVo encapsulation(Long skuId){
        SkuDetailVo skuDetailVo = new SkuDetailVo();
        //获取三级分类数据
        Result<CategoryView> categoryViewResult = skuDetailFeignClient.findCategoryBySkuId(skuId);

        //创建一个CountDown
        //CountDownLatch countDownLatch = new CountDownLatch(5);
        // 远程调用使用CompletableFuture进行异步编排
        CompletableFuture<Void> cf1 = CompletableFuture.runAsync(() -> {
            skuDetailVo.setCategoryView(categoryViewResult.getData());
            log.info(Thread.currentThread().getName() + "执行了远程调用");
        }, threadPoolExecutor);

        CompletableFuture<Void> cf2 = CompletableFuture.runAsync(() -> {
            skuDetailVo.setSkuInfo(skuDetailFeignClient.findSkuInfoAndImageBySkuId(skuId).getData());
            log.info(Thread.currentThread().getName()+"执行了远程调用");
        }, threadPoolExecutor);

        CompletableFuture<Void> cf3 = CompletableFuture.runAsync(() -> {
            skuDetailVo.setPrice(skuDetailFeignClient.findSkuById(skuId).getData().getPrice());
            log.info(Thread.currentThread().getName() + "执行了远程调用");
        }, threadPoolExecutor);

        CompletableFuture<Void> cf4 = CompletableFuture.runAsync(() -> {
            skuDetailVo.setSpuSaleAttrList(skuDetailFeignClient.findSpuSalAttrAndValueBySkuId(skuId).getData());
            log.info(Thread.currentThread().getName() + "执行了远程调用");
        }, threadPoolExecutor);

        CompletableFuture<Void> cf5 = CompletableFuture.runAsync(() -> {
            List<AttrValueConcat> attrValueConcatList = skuDetailFeignClient.findAttrValueConcatBySkuId(skuId).getData();
            Map<String, Long> map = attrValueConcatList.stream().collect(Collectors.toMap(
                    attrValueConcatVo -> attrValueConcatVo.getAttrValueConcat(), attrValueConcatVo -> attrValueConcatVo.getSkuId()
            ));
            String jsonString = JSON.toJSONString(map);
            skuDetailVo.setValuesSkuJson(jsonString);
            log.info(Thread.currentThread().getName()+"执行了远程调用");
        }, threadPoolExecutor);
        /*threadPoolExecutor.submit(()->{
            skuDetailVo.setCategoryView(categoryViewResult.getData());
            log.info(Thread.currentThread().getName()+"执行了远程调用");
            countDownLatch.countDown();
        });

        threadPoolExecutor.submit(()->{
            skuDetailVo.setSkuInfo(skuDetailFeignClient.findSkuInfoAndImageBySkuId(skuId).getData());
            log.info(Thread.currentThread().getName()+"执行了远程调用");
            countDownLatch.countDown();
        });

        threadPoolExecutor.submit(()->{
            skuDetailVo.setPrice(skuDetailFeignClient.findSkuById(skuId).getData().getPrice());
            log.info(Thread.currentThread().getName()+"执行了远程调用");
            countDownLatch.countDown();
        });

        threadPoolExecutor.submit(()->{
            skuDetailVo.setSpuSaleAttrList(skuDetailFeignClient.findSpuSalAttrAndValueBySkuId(skuId).getData());
            log.info(Thread.currentThread().getName()+"执行了远程调用");
            countDownLatch.countDown();
        });

        threadPoolExecutor.submit(()->{
            List<AttrValueConcat> attrValueConcatList = skuDetailFeignClient.findAttrValueConcatBySkuId(skuId).getData();
            Map<String, Long> map = attrValueConcatList.stream().collect(Collectors.toMap(
                    attrValueConcatVo -> attrValueConcatVo.getAttrValueConcat(), attrValueConcatVo -> attrValueConcatVo.getSkuId()
            ));
            String jsonString = JSON.toJSONString(map);
            skuDetailVo.setValuesSkuJson(jsonString);
            log.info(Thread.currentThread().getName()+"执行了远程调用");
            countDownLatch.countDown();
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        //阻塞当前线程，等其他线程执行完毕以后再执行当前主线程
        CompletableFuture.allOf(cf1,cf2,cf3,cf4,cf5).join();
        return skuDetailVo;
    }

    public SkuDetailVo skuDetailVo01(Long skuId) {
        /*String uuid = UUID.randomUUID().toString().replace("-", "");*/

        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(GmallConstant.REDIS_SKUID_BLOOM_FILTER);
        boolean contains = bloomFilter.contains(skuId);
        //查询布隆过滤器
        if (!contains){
            log.info("布隆过滤器中没有该值，直接返回null-----------");
            return null;
        }
        //查询缓存
        SkuDetailVo skuDetailVoForRedis = redisCacheService.getData(GmallConstant.REDIS_CACHE_SKU_PREFIX + skuId, SkuDetailVo.class);
        if (skuDetailVoForRedis != null) {
            log.info("从redis中命中缓存-----");
            return skuDetailVoForRedis;
        }
        //加锁

        RLock rlock = redissonClient.getLock(GmallConstant.REDIS_ITEM_LOCK_PREFIX + skuId);
        boolean lock = rlock.tryLock();
        if (lock){
            log.info("{}线程抢占锁成功-----------------",Thread.currentThread().getId());
            SkuDetailVo skuDetailVo = null;
            try {
                //从数据库查询，加入到redis缓存中
                skuDetailVo = this.encapsulation(skuId);
                if (skuDetailVo == null){
                    log.info("{}线程查询数据库不存在此数据，redis中缓存X---------------",Thread.currentThread().getId());
                    redisCacheService.saveDataRedis(GmallConstant.REDIS_CACHE_SKU_PREFIX+skuId,GmallConstant.DB_NULL_CATEGORY_VALUE);
                }else {
                    log.info("{}线程查询数据库存在此数据，redis中缓存skuDetailVo---------------",Thread.currentThread().getId());
                    redisCacheService.saveDataRedis(GmallConstant.REDIS_CACHE_SKU_PREFIX+skuId,skuDetailVo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                rlock.unlock();
            }
            log.info("{}线程释放锁成功-------",Thread.currentThread().getId());
            //返回数据
            return skuDetailVo;
        }else {
            while (skuDetailVoForRedis == null) {
                log.info("{}线程缓存未命中",Thread.currentThread().getId());
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                skuDetailVoForRedis = redisCacheService.getData(GmallConstant.REDIS_CACHE_SKU_PREFIX+skuId, SkuDetailVo.class);
            }
            log.info("{}线程从redis中命中缓存--------",Thread.currentThread().getId());
            return skuDetailVoForRedis;
        }
    }

    @Override
    @GmallCache(
            cacheKey = GmallConstant.REDIS_CACHE_SKU_PREFIX + "#{#params[0]}",
            bloomFilterName = GmallConstant.REDIS_SKUID_BLOOM_FILTER,
            bloomFilterValue = "#{#params[0]}",
            lockName = GmallConstant.REDIS_ITEM_LOCK_PREFIX+"#{#params[0]}",
            enableLock = true)
    public SkuDetailVo skuDetailVo(Long skuId) {
        SkuDetailVo skuDetailVo = this.encapsulation(skuId);
        return skuDetailVo;
    }

    @Override
    public void updateHotScore(Long skuId) {
        Long increment = redisTemplate.opsForValue().increment(GmallConstant.REDIS_SKU_HOTSCORE_PRE + skuId);
        if (increment % 5 == 0){
            //远程调用search服务方法
            goodsFeignClient.updateHotScore(skuId,increment.intValue());
        }
    }
   /*
    对象初始化阶段，最对象创建后，依赖注入完成后立即执行该方法
    @Override
    public void afterPropertiesSet() throws Exception {

    }*/
}
