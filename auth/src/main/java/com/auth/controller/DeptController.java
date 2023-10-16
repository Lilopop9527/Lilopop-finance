package com.auth.controller;

import com.auth.pojo.vo.DeptVO;
import com.auth.service.DeptService;
import com.common.core.pojo.CommonData;
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
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    private DeptService deptService;
    @GetMapping("/l")
    public CommonData<List<DeptVO>> getDepts(){
        return new CommonData<>(200,"success",deptService.getAllDepts());
    }
}
