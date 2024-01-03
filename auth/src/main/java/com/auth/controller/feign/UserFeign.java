package com.auth.controller.feign;

import com.auth.service.UserService;
import com.common.core.pojo.CommonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Lilopop
 * @description:
 */
@RestController
@RequestMapping("/feign")
public class UserFeign {
    @Autowired
    private UserService userService;
    @GetMapping("/check")
    public CommonData<Boolean> checkUser(Long id,String name){
        Boolean b = userService.checkUser(id, name);
        return new CommonData<>(200,"success",b);
    }
}
