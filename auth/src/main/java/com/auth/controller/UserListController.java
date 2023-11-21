package com.auth.controller;

import com.auth.pojo.vo.UserVO;
import com.auth.service.UserService;
import com.common.core.pojo.CommonData;
import com.common.core.pojo.PageBody;
import com.common.interceptor.annotation.CheckRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Lilopop
 * @description:
 */
@RestController
@RequestMapping("/l")
public class UserListController {
    @Autowired
    private UserService userService;
    @GetMapping("/all")
    @CheckRole(5000000)
    public CommonData<PageBody<UserVO>> getUserList(Integer pageNum,Integer pageSize){
        PageBody<UserVO> vos = userService.listUser(pageNum-1,pageSize);
        return new CommonData<>(200,"success",vos);
    }

    @GetMapping("/ur")
    @CheckRole(5000000)
    public CommonData<PageBody<UserVO>> getUserListByUsername(Integer pageNum,Integer pageSize,String username){
        PageBody<UserVO> vos = userService.listUsernameByPage(pageNum-1, pageSize, username);
        return new CommonData<>(200,"success",vos);
    }

    @GetMapping("/e")
    @CheckRole(5000000)
    public CommonData<PageBody<UserVO>> getUserListByEmail(Integer pageNum,Integer pageSize,String email){
        PageBody<UserVO> vos = userService.listEmailByPage(pageNum-1, pageSize, email);
        return new CommonData<>(200,"success",vos);
    }

    @GetMapping("/p")
    @CheckRole(5000000)
    public CommonData<PageBody<UserVO>> getUserListByPhone(String phone){
        PageBody<UserVO> vos = userService.getByPhone(phone);
        return new CommonData<>(200,"success",vos);
    }

    @GetMapping("/dept")
    @CheckRole(5000000)
    public CommonData<PageBody<UserVO>> getUserListByDept(Integer pageNum,Integer pageSize,Long deptId){
        PageBody<UserVO> vos = userService.getByDept(pageNum-1, pageSize, deptId);
        return new CommonData<>(200,"success",vos);
    }

    @GetMapping("/sta")
    @CheckRole(5000000)
    public CommonData<PageBody<UserVO>> getUserListBySta(Integer pageNum,Integer pageSize,Long staId){
        PageBody<UserVO> vos = userService.getBySta(pageNum-1, pageSize, staId);
        return new CommonData<>(200,"success",vos);
    }
}
