package com.atguigu.gmall.user.service;

import com.atguigu.gmall.user.dto.UserLoginDTO;
import com.atguigu.gmall.user.entity.UserInfo;
import com.atguigu.gmall.user.vo.UserLoginSuccessVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Light of hope
* @description 针对表【user_info(用户表)】的数据库操作Service
* @createDate 2023-07-24 15:43:02
*/
public interface UserInfoService extends IService<UserInfo> {

    public abstract UserLoginSuccessVo login(UserLoginDTO userLoginDTO);

    public abstract void logout(String token);
}
