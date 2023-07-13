package com.atguigu.gmall.product.mapper;


import com.atguigu.gmall.product.entity.BaseAttrInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
* @author Light of hope
* @description 针对表【base_attr_info(属性表)】的数据库操作Mapper
* @createDate 2023-07-10 21:27:14
* @Entity com.atguigu.gmall.product.entity.BaseAttrInfo
*/
@Mapper
public interface BaseAttrInfoMapper extends BaseMapper<BaseAttrInfo> {

    public abstract List<BaseAttrInfo> findAttrInfoList(Long c1Id, Long c2Id, Long c3Id);
}




