package com.atguigu.gmall.canal.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.atguigu.gmall.common.constant.GmallConstant;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * @Description：监听器
 * @Author: scv
 * @CreateTime: 2023-07-18  20:26
 * @Version: 1.0
 */
@CanalEventListener //声明当前类是canal的一个时间监听器
@Slf4j
public class CanalSkuInfoListener {
    @Autowired
    private RedisTemplate redisTemplate;
    @ListenPoint(destination = "example", schema = "gmall_product", table = {"sku_info"})
    public void listenerSkuInfoChange(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
        beforeColumnsList.stream().forEach(column -> {
            String columnName = column.getName();
            String columnValue = column.getValue();
            System.out.println("columnName = " + columnName);
            System.out.println("columnValue = " + columnValue);
        });

        System.out.println("----------------------------------------------------------");

        List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
        afterColumnsList.stream().forEach(column -> {
            String columnName = column.getName();
            if ("id".equalsIgnoreCase(columnName)){
                String columnValue = column.getValue();
                System.out.println(columnValue);//skuDetailVo49
                System.out.println(GmallConstant.REDIS_CACHE_SKU_PREFIX + columnValue);
                Boolean delete = redisTemplate.delete(GmallConstant.REDIS_CACHE_SKU_PREFIX + columnValue);
                System.out.println(delete);
                log.info("canal client 工作了！！ ");
            }
        });
    }
}
