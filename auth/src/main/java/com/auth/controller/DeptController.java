package com.auth.controller;

import com.auth.pojo.vo.DeptVO;
import com.auth.service.DeptService;
import com.common.core.pojo.CommonData;
import com.common.interceptor.annotation.CheckRole;
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
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    private DeptService deptService;
    @GetMapping("/l")
    @CheckRole
    public CommonData<List<DeptVO>> getDepts(){
        return new CommonData<>(200,"success",deptService.getAllDepts());
    }

    @GetMapping("/get")
    @CheckRole
    public CommonData<List<DeptVO>> getDeptsByUserId(Long id){
        List<DeptVO> vos = deptService.getDeptsByUserId(id);
        return new CommonData<>(200,"success",vos);
    }

    @PutMapping("/utd")
    @CheckRole(5000000)
    public CommonData<List<DeptVO>> userToDept(Long userId,Long[] deptIds){
        List<DeptVO> vos = deptService.changeUserToDept(userId, deptIds);
        return new CommonData<>(200,"success",vos);
    }
}
