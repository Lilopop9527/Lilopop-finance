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

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserToRoleRepository userToRoleRepository;
    public void saveUser(User user){
        Object u = userRepository
                .findOneUser(
                        ObjectUtil.isEmpty(user.getUsername())?null:user.getUsername(),
                        ObjectUtil.isEmpty(user.getPhone())?null:user.getPhone(),
                        ObjectUtil.isEmpty(user.getEmail())?null:user.getEmail());
        if(ObjectUtil.isNotEmpty(u)){
            //TODO 添加日志
            Asserts.fail("用户已存在");
        }
        user.setDeleated(0);
        UserDetail detail = new UserDetail();
        detail.setDeleated(0);
        user.setUserDetail(detail);
        userRepository.save(user);
    }

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

    public Page<User> listUsernameByPage(Integer pageNum, Integer pageSize, String username){
        //TODO 分页对象应该封装一下
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        StringBuilder bu = new StringBuilder("%");
        bu.append(username).append("%");
        return userRepository.findByUsernameLikeAndUsernameIsNotNullOrderById(bu.toString(),pageable);
    }

    public Page<User> listEmailByPage(Integer pageNum, Integer pageSize, String email){
        //TODO 分页对象应该封装一下
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        StringBuilder bu = new StringBuilder("%");
        bu.append(email).append("%");
        return userRepository.findByEmailLikeAndEmailIsNotNull(bu.toString(),pageable);
    }

    public List<User> getByPhone(String phone){
        Optional<User> o = userRepository.findUserByPhoneAndPhoneIsNotNull(phone);
        if (o.isEmpty()){
            return new ArrayList<>();
        }
        List<User> list = new ArrayList<>();
        list.add(o.get());
        return list;
    }

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
