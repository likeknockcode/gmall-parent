package com.atguigu.gmall.user.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import com.atguigu.gmall.user.dto.UserLoginDTO;
import com.atguigu.gmall.user.service.UserInfoService;
import com.atguigu.gmall.user.vo.UserLoginSuccessVo;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description：用户登录Controller
 * @Author: scv
 * @CreateTime: 2023-07-24  20:33
 * @Version: 1.0
 */
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;
    @PostMapping("/passport/login")
    public Result<UserLoginSuccessVo> login(@RequestBody UserLoginDTO userLoginDTO){
        UserLoginSuccessVo userLoginSuccessVo  = userInfoService.login(userLoginDTO);
        return Result.build(userLoginSuccessVo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("passport/logout")
    public Result logout(@RequestHeader String token){
        userInfoService.logout(token);
        return Result.ok();
    }
}
