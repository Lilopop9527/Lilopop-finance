package com.auth.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.auth.dao.*;
import com.auth.pojo.base.*;
import com.auth.pojo.dto.RoleDTO;
import com.auth.pojo.vo.RoleVO;
import com.auth.pojo.vo.StationVO;
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
     * @param roleName 角色名
     * @param weight 权重
     */
    public RoleVO saveRole(String roleName,Integer weight){
        Integer count = roleRepository.countRoleByRoleName(roleName);
        if (count >0)
            Asserts.fail("用户已存在");
        Role role = new Role(roleName,weight);
        role.setDeleated(0);
        Role temp = roleRepository.save(role);
        return new RoleVO(temp.getId(), temp.getRoleName(),temp.getWeight(),temp.getDeleated());
    }
    /**
     * 更新角色对象
     * @param dto 角色对象
     */
    public void updateRole(RoleDTO dto){
        if (ObjectUtil.isEmpty(dto.getId()))
            Asserts.fail("请输入正确id");
        Role role = roleRepository.findRoleById(dto.getId());
        if (ObjectUtil.isEmpty(role))
          Asserts.fail("未找到指定角色");
        role.setRoleName(dto.getRoleName());
        role.setWeight(dto.getWeight());
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
            RoleVO vo = new RoleVO(r.getId(),r.getRoleName(),r.getWeight(),r.getDeleated());
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
     * 设置角色和路由的关系
     * @param routeIds 路由id
     * @param roleId 角色id
     * @return 生成的关系对象
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

    /**
     * 根据用户id查找对应角色
     * @param id 用户id
     * @return 角色对象列表
     */
    public List<RoleVO> getRoleByUserId(Long id){
        List<UserToRole> utrs = userToRoleRepository.findUserToRolesById_UserId(id);
        List<RoleVO> vos = new ArrayList<>();
        for (UserToRole utr:utrs) {
            RoleVO vo = new RoleVO();
            BeanUtil.copyProperties(utr.getRole(),vo);
            vos.add(vo);
        }
        return vos;
    }
    /**
     * 更新用户和角色之间的关系
     * @param userId 用户id
     * @param roleIds 修改后的部门id
     * @return 修改后的角色列表
     */
    @Transactional
    public List<RoleVO> changeUserToRole(Long userId, Long[] roleIds){
        Integer integer = userToRoleRepository.deleteByUserId(userId);
        if (integer == 0)
            Asserts.fail("没有删除数据");
        if (roleIds.length == 0)return  new ArrayList<>();
        List<Role> roles = roleRepository.findRoleByIdIn(roleIds);
        if (roles.isEmpty())
            Asserts.fail("未找到对应部门信息");
        User user = userRepository.findUserById(userId);
        List<UserToRole> utrs = new ArrayList<>();
        for (Role r:roles) {
            UserRoleId udi = new UserRoleId(userId,r.getId());
            UserToRole utd = new UserToRole(udi,user,r);
            utrs.add(utd);
        }
        userToRoleRepository.saveAll(utrs);
        return createVOs(roles);
    }

    /**
     * 修改角色启用状态
     * @param id 角色id
     * @param del 需要修改的状态
     */
    public void changeRoleStatus(Long id,Integer del){
        Role role = roleRepository.findRoleById(id);
        if (ObjectUtil.isEmpty(role))
            Asserts.fail("未找到角色");
        if (Objects.equals(role.getDeleated(), del)){
            role.setDeleated((del+1)%2);
            roleRepository.save(role);
        }else
            Asserts.fail("用户不需要修改");
    }



    public List<RoleVO> createVOs(List<Role> roles){
        List<RoleVO> vos = new ArrayList<>();
        for (Role r:roles) {
            RoleVO vo = new RoleVO(r.getId(),r.getRoleName(),r.getWeight(),r.getDeleated());
            vos.add(vo);
        }
        return vos;
    }
}
