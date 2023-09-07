package com.auth.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.auth.dao.UserRepository;
import com.auth.dao.UserToRoleRepository;
import com.auth.pojo.base.User;
import com.auth.pojo.base.UserDetail;
import com.auth.pojo.base.UserToRole;
import com.auth.pojo.vo.UserVO;
import com.common.core.exception.Asserts;
import com.common.core.pojo.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 *@author: Lilopop
 *@description:用户基本信息
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserToRoleRepository userToRoleRepository;

    /**
     * 保存用户信息
     * @param user 用户对象
     * @return 存储结果
     */
    public boolean saveUser(User user){
        String username = user.getUsername();
        String email = user.getEmail();
        User u = null;
        if (ObjectUtil.isNotEmpty(username)){
            u = userRepository.findUserByUsername(username);
        }
        if (ObjectUtil.isEmpty(u)&&ObjectUtil.isNotEmpty(email)){
            u = userRepository.findUserByEmail(email);
        }
        if(ObjectUtil.isNotEmpty(u)){
            return false;
            //Asserts.fail(200,"用户已存在");
        }
        user.setDeleated(0);
        UserDetail detail = new UserDetail();
        detail.setDeleated(0);
        user.setUserDetail(detail);
        userRepository.save(user);
        return true;
    }
    /**
     * 更新用户信息（待处理）
     * @param u 用户对象
     */
    public void updateUser(User u){
        Optional<User> o = userRepository.findById(u.getId());
        if(o.isEmpty()){
            //TODO 添加日志
            Asserts.fail("用户基础信息不存在");
        }
        User user = o.get();
//        if(ObjectUtil.isNotEmpty(u.getUsername())){
//            user.setUsername(u.getUsername());
//        }
        if(ObjectUtil.isNotEmpty(u.getPassword())){
            user.setPassword(u.getPassword());
        }
        if(ObjectUtil.isNotEmpty(u.getPhone())){
            user.setPhone(u.getPhone());
        }
        if(ObjectUtil.isNotEmpty(u.getEmail())){
            user.setEmail(u.getEmail());
        }
        if(ObjectUtil.isNotEmpty(u.getImg())){
            user.setImg(u.getImg());
        }
        userRepository.save(user);
    }

    /**
     * 根据用户名查找用户数据
     * @param pageNum 需要查询的页数
     * @param pageSize 每页数据量
     * @param username 用户名关键字
     * @return 分页对象
     */
    public Page<User> listUsernameByPage(Integer pageNum, Integer pageSize, String username){
        //TODO 分页对象应该封装一下
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        StringBuilder bu = new StringBuilder("%");
        bu.append(username).append("%");
        return userRepository.findByUsernameLikeAndUsernameIsNotNullOrderById(bu.toString(),pageable);
    }
    /**
     * 根据邮箱查找用户数据
     * @param pageNum 需要查询的页数
     * @param pageSize 每页数据量
     * @param email 邮箱关键字
     * @return 分页对象
     */
    public Page<User> listEmailByPage(Integer pageNum, Integer pageSize, String email){
        //TODO 分页对象应该封装一下
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        StringBuilder bu = new StringBuilder("%");
        bu.append(email).append("%");
        return userRepository.findByEmailLikeAndEmailIsNotNull(bu.toString(),pageable);
    }
    /**
     * 根据手机号查找用户数据
     * @param phone 手机号
     * @return user对象
     */
    public List<User> getByPhone(String phone){
        Optional<User> o = userRepository.findUserByPhoneAndPhoneIsNotNull(phone);
        if (o.isEmpty()){
            return new ArrayList<>();
        }
        List<User> list = new ArrayList<>();
        list.add(o.get());
        return list;
    }

    /**
     * 修改逻辑删除状态
     * @param id 用户id
     * @param deleted 删除状态
     */
    public void changeUserDeleted(Long id,Integer deleted){
        Optional<User> o = userRepository.findById(id);
        if (o.isEmpty()){
            Asserts.fail("用户不存在");
        }
        User user = o.get();
        if (Objects.equals(user.getDeleated(), deleted)||deleted<0||deleted>1)
            return;
        user.setDeleated(deleted);
        userRepository.save(user);
    }

    /**
     * 根据角色查询对应用户
     * @param roleIds 角色id列表
     * @return userVO列表
     */
    public List<UserVO> getUsersByRole(List<Long> roleIds){
        List<UserVO> vos = new ArrayList<>();
        List<UserToRole> roles = userToRoleRepository.findUserToRolesById_RoleIdIn(roleIds);
        for (UserToRole ur:roles) {
            User u = ur.getUser();
            UserVO vo = new UserVO();
            BeanUtil.copyProperties(u,vo);
            vos.add(vo);
        }
        return vos;
    }
}
