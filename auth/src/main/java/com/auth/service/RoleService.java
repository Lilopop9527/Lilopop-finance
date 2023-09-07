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
/**
 *@author: Lilopop
 *@description:角色信息相关业务
 */
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

    /**
     * 存储角色对象
     * @param role 角色对象
     */
    public void saveRole(Role role){
        role.setDeleated(0);
        roleRepository.save(role);
    }
    /**
     * 更新角色对象
     * @param role 角色对象
     */
    public void updateRole(Role role){
        if (ObjectUtil.isEmpty(role.getDeleated())
                ||ObjectUtil.isEmpty(role.getId())
                ||ObjectUtil.isEmpty(role.getRoleName())){
            Asserts.fail("角色信息不合法!");
        }
        roleRepository.save(role);
    }

    /**
     * 获取所有角色对象
     * @return 角色对象列表
     */
    public List<RoleVO> getRoles(){
        List<RoleVO> vos = new ArrayList<>();
        List<Role> roles = roleRepository.findAll();
        for (Role r:roles) {
            RoleVO vo = new RoleVO(r.getId(),r.getRoleName());
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 根据id获取角色
     * @param id 角色id
     * @return 角色对象
     */
    public Role getRoleById(Long id){
        return roleRepository.findRoleById(id);
    }

    /**
     * 为用户设置角色关系
     * @param userId 用户id
     * @param roleIds 角色id列表
     */
    public void saveUserToRole(Long userId,List<Long> roleIds){
        List<UserToRole> lists = getUserAndRoles(userId,roleIds);
        userToRoleRepository.saveAll(lists);
    }

    /**
     * 删除用户和角色的关联
     * @param userId 用户id
     * @param roleIds 角色id列表
     */
    public void delUserToRole(Long userId,List<Long> roleIds){
        List<UserToRole> lists = getUserAndRoles(userId,roleIds);
        userToRoleRepository.deleteAll(lists);
    }

    /**
     * 获取用户和角色关联信息
     * @param userId 用户id
     * @param roleIds 角色id列表
     * @return 所需用户的角色信息
     */
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

    /**
     * 根据角色获取用户
     * @param roleId 角色对象
     * @param pageNum 当前查询页
     * @param pageSize 每页数据量
     * @return 分页对象
     */
    public Page<UserToRole> getUsersByRole(Long roleId,Integer pageNum,Integer pageSize){
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        return userToRoleRepository.findUserToRolesById_RoleId(roleId,pageable);
    }

    /**
     * 设置角色访问权限
     * @param routeIds 路径id列表
     * @param roleId 角色id
     */
    public void saveRoleToRoutes(List<Long> routeIds,Long roleId){
        roleToRoutesRepository.saveAll(getRoleToRoutes(routeIds,roleId));
    }
    /**
     * 删除角色访问权限
     * @param routeIds 路径id列表
     * @param roleId 角色id
     */
    public void delRoleToRoutes(List<Long> routeIds,Long roleId){
        roleToRoutesRepository.deleteAll(getRoleToRoutes(routeIds,roleId));
    }

    /**
     *
     * @param routeIds
     * @param roleId
     * @return
     */
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
