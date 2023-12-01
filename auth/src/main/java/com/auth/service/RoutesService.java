package com.auth.service;

import com.auth.dao.RoleToRoutesRepository;
import com.auth.dao.RoutesRepository;
import com.auth.pojo.base.Role;
import com.auth.pojo.base.RoleRoutesId;
import com.auth.pojo.base.RoleToRoutes;
import com.auth.pojo.base.Routes;
import com.auth.pojo.vo.RouteVO;
import com.common.core.exception.Asserts;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 *@author: Lilopop
 *@description:页面权限信息
 */
@Service
public class RoutesService {

    @Autowired
    private RoutesRepository routesRepository;
    @Autowired
    private RoleToRoutesRepository roleToRoutesRepository;
    @Autowired
    private RoleService roleService;

    /**
     * 存储路径
     * @param routes 路径列表
     */
    public void saveRoutes(List<Routes> routes){
        routesRepository.saveAll(routes);
    }
    /**
     * 删除路径
     * @param routes 路径id列表
     */
    public void delRoutes(List<Long> routes){
        routesRepository.deleteRoutesByIdIn(routes);
    }

    /**
     * 获取所有路径
     * @return 路径对象列表
     */
    public List<RouteVO> listRoutes(){
        List<Routes> all = routesRepository.findAll();
        Map<Long,List<Routes>> map = createMaps(all);
        return routeToVO(map,0L);
    }

    /**
     * 设置角色访问权限
     * @param route 路径id列表
     * @param roleId 角色id
     */
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

    /**
     * 查询角色权限路径
     * @param roleId 角色id
     * @return 路由列表
     */
    public List<RoleToRoutes> getRoutesByRole(Long roleId){
        return roleToRoutesRepository.findRoleToRoutesById_RoleId(roleId);
    }
    /**
     * 查询角色权限路径
     * @param roles 角色id列表
     * @return 路由列表
     */
    public List<RouteVO> getRoutesByRole(List<Long> roles){
        Set<Routes> routes = new HashSet<>();
        List<RoleToRoutes> rtrs = roleToRoutesRepository.findRoleToRoutesById_RoleIdIn(roles);
        for (RoleToRoutes rtr:rtrs) {
            routes.add(rtr.getRoutes());
        }
        //routes.sort((a, b) -> (int) (a.getParent() - b.getParent()));
        Map<Long,List<Routes>> map = createMaps(routes);
        List<RouteVO> ans = routeToVO(map,0L);
        ans.sort((a, b) -> (int) (a.getId() - b.getId()));
        return ans;
    }

    /**
     * 根据角色id寻找对应route的id
     * @param id 角色id
     * @return route的id集合
     */
    public List<Long> getRouteIdByRoleId(Long id){
        return routesRepository.findRoutesIdByRoleId(id);
    }

    @Transactional
    public List<RouteVO> changeRoleToRoutes(Long[] rids,Long roleId){
        Integer a = roleToRoutesRepository.delRoleToRoute(roleId);
        Role role = roleService.getRoleById(roleId);
        List<Routes> routes = routesRepository.findRoutesByIdIn(rids);
        List<RoleToRoutes> rtrs = new ArrayList<>();
        for (Routes r:routes) {
            RoleRoutesId rri = new RoleRoutesId(role.getId(),r.getId());
            RoleToRoutes rtr = new RoleToRoutes(rri,role,r);
            rtrs.add(rtr);
        }
        roleToRoutesRepository.saveAll(rtrs);
        Map<Long, List<Routes>> maps = createMaps(routes);
        return routeToVO(maps,0L);
    }

    /**
     * 设置路径上下级关系
     * @param routes 每一级路由
     * @param key 当前需要设置的路由层级
     * @return key所代表的路由层级列表
     */
    public List<RouteVO> routeToVO(Map<Long,List<Routes>> routes,Long key){
        List<RouteVO> vos = new ArrayList<>();
        if (!routes.containsKey(key))
            return vos;
        for (Routes route:routes.get(key)) {
            RouteVO vo = new RouteVO(route.getId(),route.getPath(),route.getTitle());
            vo.setChildren(routeToVO(routes,route.getId()));
            vos.add(vo);
        }
        vos.sort((a, b) -> (int) (a.getId() - b.getId()));
        return vos;
    }

    public Map<Long,List<Routes>> createMaps(Iterable<Routes> arr){
        Map<Long,List<Routes>> map = new HashMap<>();
        for (Routes r:arr) {
            List<Routes> list = map.getOrDefault(r.getParent(),new ArrayList<>());
            list.add(r);
            map.put(r.getParent(),list);
        }
        return map;
    }
}
