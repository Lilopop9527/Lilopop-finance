package com.auth.service;

import com.auth.dao.RoleToRoutesRepository;
import com.auth.dao.RoutesRepository;
import com.auth.pojo.base.Role;
import com.auth.pojo.base.RoleRoutesId;
import com.auth.pojo.base.RoleToRoutes;
import com.auth.pojo.base.Routes;
import com.auth.pojo.vo.RouteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoutesService {

    @Autowired
    private RoutesRepository routesRepository;
    @Autowired
    private RoleToRoutesRepository roleToRoutesRepository;
    @Autowired
    private RoleService roleService;

    public void saveRoutes(List<Routes> routes){
        routesRepository.saveAll(routes);
    }

    public void delRoutes(List<Long> routes){
        routesRepository.deleteRoutesByIdIn(routes);
    }

    public List<Routes> listRoutes(){
        return routesRepository.findAll();
    }

    public void saveRoleToRoutes(List<Long> route,Long roleId){
        Role role = roleService.getRoleById(roleId);
        List<Routes> routes = routesRepository.findRoutesByDeleatedAndIdIn(0,route);
        List<RoleToRoutes> rtrs = new ArrayList<>();
        for (Routes r:routes) {
            RoleRoutesId id = new RoleRoutesId(roleId,r.getId());
            RoleToRoutes rtr = new RoleToRoutes(id,role,r);
            rtrs.add(rtr);
        }
        roleToRoutesRepository.saveAll(rtrs);
    }

    public List<RoleToRoutes> getRoutesByRole(Long roleId){
        return roleToRoutesRepository.findRoleToRoutesById_RoleId(roleId);
    }

    public List<RouteVO> getRoutesByRole(List<Long> roles){
        Set<Routes> routes = new HashSet<>();
        List<RoleToRoutes> rtrs = roleToRoutesRepository.findRoleToRoutesById_RoleIdIn(roles);
        for (RoleToRoutes rtr:rtrs) {
            routes.add(rtr.getRoutes());
        }
        //routes.sort((a, b) -> (int) (a.getParent() - b.getParent()));
        Map<Long,List<Routes>> map = new HashMap<>();
        for (Routes route:routes) {
            List<Routes> list = map.getOrDefault(route.getParent(),new ArrayList<>());
            list.add(route);
            map.put(route.getParent(),list);
        }
        return routeToVO(map,0L);
    }

    public List<RouteVO> routeToVO(Map<Long,List<Routes>> routes,Long key){
        List<RouteVO> vos = new ArrayList<>();
        if (!routes.containsKey(key))
            return vos;
        for (Routes route:routes.get(key)) {
            RouteVO vo = new RouteVO(route.getId(),route.getPath(),route.getTitle());
            vo.setChildren(routeToVO(routes,route.getId()));
            vos.add(vo);
        }
        return vos;
    }
}
