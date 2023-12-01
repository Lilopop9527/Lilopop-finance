package com.auth.controller;

import com.auth.pojo.vo.DeptVO;
import com.auth.service.DeptService;
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

    @PutMapping("/sta")
    @CheckRole(5000000)
    public CommonData changeStatus(Long id,Integer del){
        deptService.changeDeptStatus(id, del);
        return new CommonData<>(200,"success",null);
    }

    @PutMapping("/update")
    @CheckRole(5000000)
    public CommonData<DeptVO> updateDept(Long id,String name,Long principalId){
        DeptVO vo = deptService.updateDept(id, name, principalId);
        return new CommonData<>(200,"success",vo);
    }

    @PostMapping("/add")
    @CheckRole(5000000)
    public CommonData<DeptVO> saveDept(String name,Long principalId){
        DeptVO vo = deptService.saveDept(name, principalId);
        return new CommonData<>(200,"success",vo);
    }
}
