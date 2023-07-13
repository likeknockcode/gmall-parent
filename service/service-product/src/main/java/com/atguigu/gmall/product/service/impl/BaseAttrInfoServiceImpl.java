package com.atguigu.gmall.product.service.impl;


import com.atguigu.gmall.product.entity.BaseAttrInfo;
import com.atguigu.gmall.product.entity.BaseAttrValue;
import com.atguigu.gmall.product.mapper.BaseAttrValueMapper;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import com.atguigu.gmall.product.mapper.BaseAttrInfoMapper;
import com.atguigu.gmall.product.service.BaseAttrValueService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Light of hope
* @description 针对表【base_attr_info(属性表)】的数据库操作Service实现
* @createDate 2023-07-10 21:27:14
*/
@Service
public class BaseAttrInfoServiceImpl extends ServiceImpl<BaseAttrInfoMapper, BaseAttrInfo> implements BaseAttrInfoService {

    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    private BaseAttrValueService baseAttrValueService;

    @Override
    public List<BaseAttrInfo> findAttrInfoList(Long c1Id, Long c2Id, Long c3Id) {
        List<BaseAttrInfo> baseAttrInfoList = baseAttrInfoMapper.findAttrInfoList(c1Id,c2Id,c3Id);
        return baseAttrInfoList;
    }
    @Transactional
    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        if (baseAttrInfo.getId()==null){
            //保存baseAttrInfo
            this.save(baseAttrInfo);
            //保存baseAttrvalue
            List<BaseAttrValue> baseAttrValueList = baseAttrInfo.getAttrValueList().stream().map(baseAttrValue -> {
                baseAttrValue.setAttrId(baseAttrInfo.getId());
                return baseAttrValue;
            }).collect(Collectors.toList());
            baseAttrValueService.saveBatch(baseAttrValueList);
        }else {
            //修改平台属性信息
            this.updateById(baseAttrInfo);

            //修改平台属性值
            LambdaQueryWrapper<BaseAttrValue> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(BaseAttrValue::getAttrId,baseAttrInfo.getId());
            baseAttrValueService.remove(lambdaQueryWrapper);

            baseAttrInfo.getAttrValueList().stream().map(baseAttrValue -> {
                baseAttrValue.setAttrId(baseAttrInfo.getId());
                return baseAttrValue;
            }).forEach(baseAttrValue -> {
                baseAttrValueService.save(baseAttrValue);
            });
        }
    }
}




