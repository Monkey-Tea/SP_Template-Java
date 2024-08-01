package com.spt.sp_template.controller;

import cn.hutool.core.util.StrUtil;
import com.spt.sp_template.annotation.AuthAccess;
import com.spt.sp_template.common.Result;
import com.spt.sp_template.entity.User;
import com.spt.sp_template.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping
public class WebController {

    @Resource
    UserService userService;

    @AuthAccess //自定义注解,放开权限
    @GetMapping("/")
    public Result hello(){
        return Result.success("success");
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){
            return Result.error("数据输入不合法");
        }
       user = userService.login(user);
        return Result.success(user);
    }
    //注册
    @AuthAccess //自定义注解,放开权限
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword()) || StrUtil.isBlank(user.getRole())){
            return Result.error("数据输入不合法");
        }
        if (user.getUsername().length() > 10 || user.getPassword().length() >10){
            return Result.error("数据输入不合法");
        }
        user = userService.register(user);
        return Result.success(user);
    }

    @AuthAccess //自定义注解,放开权限
    @PutMapping("/resetPassword")
    public Result resetPassword (@RequestBody User user){
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPhone())){
            return Result.error("数据不合法");
        }
        userService.resetPassword(user);
        return Result.success();
    }




}
