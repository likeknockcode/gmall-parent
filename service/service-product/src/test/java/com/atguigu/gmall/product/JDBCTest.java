package com.atguigu.gmall.product;

import com.atguigu.gmall.product.entity.SkuImage;
import com.atguigu.gmall.product.mapper.SkuImageMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description：测试数据库读写分离
 * @Author: scv
 * @CreateTime: 2023-07-17  18:52
 * @Version: 1.0
 */
@SpringBootTest(classes = ProductServiceApplication.class)
public class JDBCTest {
    @Autowired
    private SkuImageMapper skuImageMapper;

    @Test
    public void saveImage(){
        SkuImage skuImage = new SkuImage();
        skuImage.setSkuId(101L);
        skuImage.setImgName("aaa");
        skuImage.setImgUrl("bbb");
        skuImage.setIsDefault("0");
        skuImageMapper.insert(skuImage);
    }

    @Test
    public void getSkuById() {
        for(int x = 0 ; x < 10 ; x++) {
            SkuImage skuImage = skuImageMapper.selectById(259);
            System.out.println(skuImage);
        }
    }

}
