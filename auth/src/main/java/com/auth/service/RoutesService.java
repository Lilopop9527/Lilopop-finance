package com.auth.service;

import com.auth.dao.RoleToRoutesRepository;
import com.auth.dao.RoutesRepository;
import com.auth.pojo.base.RoleToRoutes;
import com.auth.pojo.base.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutesService {

    @Autowired
    private RoutesRepository routesRepository;
    @Autowired
    private RoleToRoutesRepository roleToRoutesRepository;

    public void saveRoutes(List<Routes> routes){
        routesRepository.saveAll(routes);
    }

    public void delRoutes(List<Long> routes){
        routesRepository.deleteRoutesByIdIn(routes);
    }

    public List<Routes> listRoutes(){
        return routesRepository.findAll();
    }

    public List<RoleToRoutes> getRoutesByRole(Long roleId){
        return roleToRoutesRepository.findRoleToRoutesById_RoleId(roleId);
    }
}
