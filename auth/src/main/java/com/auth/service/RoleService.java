package com.auth.service;

import cn.hutool.core.util.ObjectUtil;
import com.auth.dao.*;
import com.auth.pojo.base.*;
import com.auth.pojo.vo.RoleVO;
import com.common.core.exception.Asserts;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserToRoleRepository userToRoleRepository;
    @Autowired
    private RoleToRoutesRepository roleToRoutesRepository;
    @Autowired
    private RoutesRepository routesRepository;

    public void saveRole(Role role){
        role.setDeleated(0);
        roleRepository.save(role);
    }

    public void updateRole(Role role){
        if (ObjectUtil.isEmpty(role.getDeleated())
                ||ObjectUtil.isEmpty(role.getId())
                ||ObjectUtil.isEmpty(role.getRoleName())){
            Asserts.fail("角色信息不合法!");
        }
        roleRepository.save(role);
    }

    public List<RoleVO> getRoles(){
        List<RoleVO> vos = new ArrayList<>();
        List<Role> roles = roleRepository.findAll();
        for (Role r:roles) {
            RoleVO vo = new RoleVO(r.getId(),r.getRoleName());
            vos.add(vo);
        }
        return vos;
    }

    public void saveUserToRole(Long userId,List<Long> roleIds){
        List<UserToRole> lists = getUserAndRoles(userId,roleIds);
        userToRoleRepository.saveAll(lists);
    }

    public void delUserToRole(Long userId,List<Long> roleIds){
        List<UserToRole> lists = getUserAndRoles(userId,roleIds);
        userToRoleRepository.deleteAll(lists);
    }

    public List<UserToRole> getUserAndRoles(Long userId,List<Long> roleIds){
        Optional<User> o1 = userRepository.findById(userId);
        List<Role> roles = roleRepository.getRoles(roleIds,0);
        if (o1.isEmpty()||roles.isEmpty())
            Asserts.fail("数据错误");
        List<UserToRole> lists = new ArrayList<>();
        for (Role r:roles) {
            UserToRole ur = new UserToRole(new UserRoleId(userId,r.getId()),o1.get(),r);
            lists.add(ur);
        }
        return lists;
    }


    public Page<UserToRole> getUsersByRole(Long roleId,Integer pageNum,Integer pageSize){
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        return userToRoleRepository.findUserToRolesById_RoleId(roleId,pageable);
    }

    public void saveRoleToRoutes(List<Long> routeIds,Long roleId){
        roleToRoutesRepository.saveAll(getRoleToRoutes(routeIds,roleId));
    }

    public void delRoleToRoutes(List<Long> routeIds,Long roleId){
        roleToRoutesRepository.deleteAll(getRoleToRoutes(routeIds,roleId));
    }

    public Set<RoleToRoutes> getRoleToRoutes(List<Long> routeIds,Long roleId){
        Optional<Role> o = roleRepository.findById(roleId);
        if (o.isEmpty())
            Asserts.fail("未找到指定角色数据");
        Role role = o.get();
        Set<RoleToRoutes> set = new HashSet<>();
        for (Long l:routeIds) {
            Routes r = routesRepository.getReferenceById(l);
            RoleToRoutes rr = new RoleToRoutes(new RoleRoutesId(roleId,l),role,r);
            set.add(rr);
        }
        return set;
    }
}
