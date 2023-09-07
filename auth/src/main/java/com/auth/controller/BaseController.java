package com.auth.controller;

import cn.hutool.core.util.ObjectUtil;
import com.auth.pojo.vo.UserVO;
import com.auth.service.LoginService;
import com.common.core.pojo.CommonData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
public class BaseController {
    @Autowired
    private LoginService loginService;
    @GetMapping("/test")
    public String test(){
        return "123456";
    }

    @PostMapping("/login")
    public CommonData<UserVO> loginByUsername(String username, String password, String email, String phone, String code){
        UserVO vo;
        if(ObjectUtil.isNotEmpty(username) &&ObjectUtil.isNotEmpty(password))
            vo = loginService.loginByUsername(username, password);
        else if (ObjectUtil.isNotEmpty(email) &&ObjectUtil.isNotEmpty(password))
            vo = loginService.loginByEmail(email, password);
        else
            vo = loginService.loginByPhone(phone,code);
        return vo == null?
                new CommonData<>(201,"登录失败，请检查输入信息",null):
                new CommonData<>(200,"success",vo);
    }

    @GetMapping("/logout")
    public CommonData<Object> logout(Long id){
        return loginService.loginout(id)?new CommonData<>(200,"success",null):new CommonData<>(500,"当前用户已过期",null);
    }
}
