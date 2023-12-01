package com.auth.controller;

import com.auth.pojo.dto.RoleDTO;
import com.auth.pojo.vo.DeptVO;
import com.auth.pojo.vo.RoleVO;
import com.auth.pojo.vo.StationVO;
import com.auth.service.RoleService;
import com.common.core.pojo.CommonData;
import com.common.interceptor.annotation.CheckRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @CheckRole
    public CommonData<List<RoleVO>> getRoleByUserId(Long id){
        List<RoleVO> vos = roleService.getRoleByUserId(id);
        return new CommonData<>(200,"success",vos);
    }

    @PostMapping("/save")
    @CheckRole(5000000)
    public CommonData<RoleVO> saveRole(String roleName,Integer weight){
        RoleVO vo = roleService.saveRole(roleName, weight);
        return new CommonData<>(200,"success",vo);
    }

    @GetMapping("/l")
    @CheckRole
    public CommonData<List<RoleVO>> getRoles(){
        List<RoleVO> vos = roleService.getRoles();
        return new CommonData<>(200,"success",vos);
    }

    @PutMapping("/utr")
    @CheckRole(5000000)
    public CommonData<List<RoleVO>> userToDept(Long userId, Long[] roleIds){
        List<RoleVO> vos = roleService.changeUserToRole(userId, roleIds);
        return new CommonData<>(200,"success",vos);
    }

    @PutMapping("/del")
    @CheckRole(5000000)
    public CommonData changeRoleStatus(Long id,Integer del){
        roleService.changeRoleStatus(id, del);
        return new CommonData<>(200,"success",null);
    }

    @PutMapping("/update")
    @CheckRole(5000000)
    public CommonData updateRole(RoleDTO dto){
        roleService.updateRole(dto);
        return new CommonData(200,"success",null);
    }
}
