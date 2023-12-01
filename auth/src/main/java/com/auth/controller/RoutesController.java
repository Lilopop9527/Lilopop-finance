package com.auth.controller;

import com.auth.pojo.vo.RouteVO;
import com.auth.service.RoutesService;
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
@RequestMapping("/routes")
public class RoutesController {
    @Autowired
    private RoutesService routesService;

    @GetMapping("/l")
    @CheckRole(5000000)
    public CommonData<List<RouteVO>> getAllRoutes(){
        return new CommonData<>(200,"success",routesService.listRoutes());
    }

    @GetMapping("/bri")
    @CheckRole(5000000)
    public CommonData<List<Long>> getRouteIdByRoleId(Long id){
        return new CommonData<>(200,"success",routesService.getRouteIdByRoleId(id));
    }

    @PutMapping("/crtr")
    @CheckRole(9000000)
    public CommonData<List<RouteVO>> changeRoleToRoutes(Long[] rids,Long roleId){
        List<RouteVO> vos = routesService.changeRoleToRoutes(rids, roleId);
        return new CommonData<>(200,"success",vos);
    }
}
