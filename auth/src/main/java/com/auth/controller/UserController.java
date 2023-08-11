package com.auth.controller;

import com.auth.pojo.base.User;
import com.auth.service.UserService;
import com.common.core.pojo.CommonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/saveUser")
    public CommonData saveUser(User user){
        boolean b = userService.saveUser(user);
        CommonData commonData;
        if (b){
            commonData = new CommonData(200,"success",null);
        }else {
            commonData = new CommonData(200,"用户重复",null);
        }
        return commonData;
    }
}
