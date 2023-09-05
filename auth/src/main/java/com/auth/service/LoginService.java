package com.auth.service;

import cn.hutool.core.util.ObjectUtil;
import com.auth.dao.UserRepository;
import com.auth.pojo.base.Role;
import com.auth.pojo.base.RoleToRoutes;
import com.auth.pojo.base.User;
import com.auth.pojo.base.UserToRole;
import com.auth.pojo.vo.RoleVO;
import com.auth.pojo.vo.UserVO;
import com.common.core.exception.Asserts;
import com.common.core.pojo.LogMessage;
import com.common.core.utils.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoutesService routesService;
    @Autowired
    private StringRedisTemplate template;

    public UserVO loginByUsername(String username, String password){
        Optional<User> o = userRepository.findUserByUsernameAndPassword(username, password);
        return o.map(this::createUser).orElse(null);
    }

    public UserVO loginByPhone(String phone,String verCode){
        //TODO 手机验证码验证
        int codeStatus = 0;
        Optional<User> o = userRepository.findUserByPhoneAndPhoneIsNotNull(phone);
        return o.map(this::createUser).orElse(null);
    }

    public UserVO loginByEmail(String email,String password){
        Optional<User> o = userRepository.findUserByEmailAndPassword(email, password);
        return o.map(this::createUser).orElse(null);
    }

    public boolean loginout(Long id){
        return Boolean.TRUE.equals(template.delete("token_" + id));
    }

    public Map<String,Object> fillMessage(User user){
        Map<String,Object> map = new HashMap<>();
        map.put("id",user.getId().toString());
        if(ObjectUtil.isNotEmpty(user.getUsername())){
            map.put("user",user.getUsername());
        }
        if(ObjectUtil.isNotEmpty(user.getPhone())){
            map.put("phone",user.getPhone());
        }
        if(ObjectUtil.isNotEmpty(user.getEmail())){
            map.put("email",user.getEmail());
        }
        if(ObjectUtil.isNotEmpty(user.getImg())){
            map.put("img",user.getImg());
        }
        return map;
    }

    public void saveToken(String token,Long id){
        template.opsForValue().set("token_"+id,token,30L,TimeUnit.HOURS);
    }

    public boolean hasToken(Long id){
        return Boolean.TRUE.equals(template.hasKey("token_" + id));
    }

    public UserVO createUser(User user){
        String token;
        if (!hasToken(user.getId())){
            Map<String,Object> map = fillMessage(user);
            token = JWTUtil.createToken(map);
            saveToken(token,user.getId());
        }else{
            token = template.opsForValue().get("token_"+user.getId());
        }
        List<Role> roles = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for (UserToRole utr:user.getRoles()) {
            Role r = utr.getRole();
            roles.add(r);
            ids.add(r.getId());
        }
        UserVO vo = new UserVO(user.getId(), user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getImg(), token,roleToVO(roles));
        vo.setRoutes(routesService.getRoutesByRole(ids));
        return vo;
    }
    public List<RoleVO> roleToVO(List<Role> roles){
        List<RoleVO> vos = new ArrayList<>();
        for (Role r:roles) {
            vos.add(new RoleVO(r.getId(),r.getRoleName()));
        }
        return vos;
    }
}
