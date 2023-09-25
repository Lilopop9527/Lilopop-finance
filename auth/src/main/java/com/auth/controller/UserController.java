package com.auth.controller;

import cn.hutool.core.util.ObjectUtil;
import com.auth.pojo.base.User;
import com.auth.pojo.vo.DetailVO;
import com.auth.service.UserDetailService;
import com.auth.service.UserService;
import com.common.core.pojo.CommonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailService detailService;
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

    @PutMapping("/upU")
    public CommonData updateUsername(String username,Long id){
        if (userService.updateUsername(username, id)){
            return new CommonData(200,"success",null);
        }else
            return new CommonData(500,"更新失败，请稍后再试",username);
    }

    @PutMapping("/upE")
    public CommonData updateEmail(String email,Long id){
        if (userService.updateEmail(email, id)){
            return new CommonData(200,"success",null);
        }else
            return new CommonData(500,"更新失败，请稍后再试",email);
    }

    @PutMapping("/upP")
    public CommonData updatePhone(String phone,Long id){
        if (userService.updatePhone(phone, id)){
            return new CommonData(200,"success",null);
        }else
            return new CommonData(500,"更新失败，请稍后再试",phone);
    }

    @PutMapping("/upPsd")
    public CommonData updatePsd(String password1,String password2,Long id){
        if (ObjectUtil.equals(password1,password2))
            return new CommonData(201,"输入的密码一致",null);
        if (userService.updatePassword(password1, password2, id)){
            return new CommonData(200,"success",null);
        }else
            return new CommonData(500,"更新失败，请稍后再试",password2);
    }

    @PostMapping("/img/{id}")
    public CommonData<String> updateImg(MultipartFile file,@PathVariable("id") Long id){
        String s = userService.updateImg(file, id);
        return new CommonData<>(200,"success",s);
    }

    @PutMapping("/detail")
    public CommonData<DetailVO> updateDetail(DetailVO detail){
        DetailVO vo = detailService.saveDetail(detail);
        return new CommonData<>(200,"success",vo);
    }
}
