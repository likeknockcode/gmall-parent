package com.atguigu.gmall.product;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;


/**
 * @Description：布隆过滤器Demo
 * @Author: scv
 * @CreateTime: 2023-07-17  20:35
 * @Version: 1.0
 */
public class BloomFilterTest {
    public static void main(String[] args) {
        /**
         * Funnel: 通道，构建布隆过滤器的时候所需要的对象
         * expectedInsertions: 布隆过滤器中存储的数据量的大小
         * fpp: 误判率
         */
        BloomFilter<Long> bloomFilter = BloomFilter.create(Funnels.longFunnel(), 1000000, 0.00001);

        //添加数据到布隆过滤器

        bloomFilter.put(23L);
        bloomFilter.put(40L);
        bloomFilter.put(101L);

        //判断是否存在
        System.out.println("bloomFilter.mightContain(23L) = " + bloomFilter.mightContain(23L));
        System.out.println("bloomFilter.mightContain(100L) = " + bloomFilter.mightContain(100L));
        System.out.println("bloomFilter.mightContain(101L) = " + bloomFilter.mightContain(101L));
    }
}
