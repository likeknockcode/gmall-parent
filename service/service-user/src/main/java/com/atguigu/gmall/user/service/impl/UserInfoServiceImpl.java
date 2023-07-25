package com.atguigu.gmall.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.common.constant.GmallConstant;
import com.atguigu.gmall.common.execption.GmallException;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.user.dto.UserLoginDTO;
import com.atguigu.gmall.user.vo.UserLoginSuccessVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gmall.user.entity.UserInfo;
import com.atguigu.gmall.user.service.UserInfoService;
import com.atguigu.gmall.user.mapper.UserInfoMapper;
import org.apache.commons.codec.cli.Digest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
* @author Light of hope
* @description 针对表【user_info(用户表)】的数据库操作Service实现
* @createDate 2023-07-24 15:43:02
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService{
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public UserLoginSuccessVo login(UserLoginDTO userLoginDTO) {
        String loginName = userLoginDTO.getLoginName();
        LambdaQueryWrapper<UserInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserInfo::getLoginName,loginName);
        UserInfo userInfo = getOne(lambdaQueryWrapper);
        if (userInfo == null){
            throw new GmallException(ResultCodeEnum.LOGIN_ERROR);
        }

        //校验密码
        String passwd = userLoginDTO.getPasswd();

        String md5Passwd = DigestUtils.md5DigestAsHex(passwd.getBytes(StandardCharsets.UTF_8));
        if (!userInfo.getPasswd().equals(md5Passwd)){
            throw new GmallException(ResultCodeEnum.LOGIN_ERROR);
        }

        //token生成
        String token = UUID.randomUUID().toString().replace("-", "");
        //存储到Redis中
        redisTemplate.opsForValue().set(GmallConstant.REDIS_LOGIN_USER+token, JSON.toJSONString(userInfo));

        //封装结果数据返回
        UserLoginSuccessVo userLoginSuccessVo = new UserLoginSuccessVo();
        userLoginSuccessVo.setToken(token);
        userLoginSuccessVo.setNickName(userInfo.getNickName());

        return userLoginSuccessVo;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(GmallConstant.REDIS_LOGIN_USER+token);
    }
}




