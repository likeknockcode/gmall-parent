package com.atguigu.gmall.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description：跳转登录页面
 * @Author: scv
 * @CreateTime: 2023-07-24  15:26
 * @Version: 1.0
 */
@Controller
public class LoginController {
    @GetMapping(value = "login.html")
    public String login(@RequestParam("originUrl") String originUrl, Model model){
        model.addAttribute("originUrl",originUrl);
        return "login";
    }
}
