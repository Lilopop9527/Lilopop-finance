package com.auth.controller;

import com.auth.pojo.vo.DeptVO;
import com.auth.pojo.vo.RoleVO;
import com.auth.pojo.vo.StationVO;
import com.auth.service.RoleService;
import com.common.core.pojo.CommonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Lilopop
 * @description:
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping("/get")
    public CommonData<List<RoleVO>> getRoleByUserId(Long id){
        List<RoleVO> vos = roleService.getRoleByUserId(id);
        return new CommonData<>(200,"success",vos);
    }

    @GetMapping("/l")
    public CommonData<List<RoleVO>> getRoles(){
        List<RoleVO> vos = roleService.getRoles();
        return new CommonData<>(200,"success",vos);
    }

    @PutMapping("/utr")
    public CommonData<List<RoleVO>> userToDept(Long userId, Long[] roleIds){
        List<RoleVO> vos = roleService.changeUserToRole(userId, roleIds);
        return new CommonData<>(200,"success",vos);
    }
}
