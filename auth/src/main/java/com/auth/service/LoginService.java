package com.auth.service;

import cn.hutool.core.util.ObjectUtil;
import com.auth.dao.UserRepository;
import com.auth.pojo.base.User;
import com.auth.pojo.vo.UserVO;
import com.common.core.exception.Asserts;
import com.common.core.pojo.LogMessage;
import com.common.core.utils.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private StreamBridge streamBridge;
    public UserVO loginByUsername(String username, String password){
        Optional<User> o = userRepository.findUserByUsernameAndPassword(username, password);
        if (o.isPresent()){
            return createUser(o.get());
        }else {
//            StringBuffer bu = new StringBuffer("username:");
//            bu.append(username).append(",password:").append(password);
//            StringBuffer bu2 = new StringBuffer(this.getClass().toString());
//            bu2.append(".").append("loginByUsername");
//            LogMessage msg = LogMessage.builder("auth",bu.toString(),2, bu2.toString());
//            streamBridge.send("logSend", msg);
            Asserts.fail("登陆失败，请检查账号密码");
            return null;
        }
    }

    public UserVO loginByPhone(String phone,String verCode){
        //TODO 手机验证码验证
        int codeStatus = 0;
        Optional<User> o = userRepository.findUserByPhoneAndPhoneIsNotNull(phone);
        if (o.isPresent()){
            return createUser(o.get());
        }else {
//            StringBuffer bu = new StringBuffer("phone:");
//            bu.append(phone).append(",codeStatus:").append(codeStatus);
//            StringBuffer bu2 = new StringBuffer(this.getClass().toString());
//            bu2.append(".").append("loginByPhone");
//            LogMessage msg = LogMessage.builder("auth",bu.toString(),2, bu2.toString());
//            streamBridge.send("logSend", msg);
            Asserts.fail("登陆失败，请检查手机号码和验证码");
            return null;
        }
    }

    public UserVO loginByEmail(String email,String password){
        Optional<User> o = userRepository.findUserByEmailAndPassword(email, password);
        if (o.isPresent()){
            return createUser(o.get());
        }else {
//            StringBuffer bu = new StringBuffer("email:");
//            bu.append(email).append(",password:").append(password);
//            StringBuffer bu2 = new StringBuffer(this.getClass().toString());
//            bu2.append(".").append("loginByEmail");
//            LogMessage msg = LogMessage.builder("auth",bu.toString(),2, bu2.toString());
//            streamBridge.send("logSend", msg);
            Asserts.fail("登陆失败，请检查邮箱和密码");
            return null;
        }
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
        template.opsForValue().set("token_"+id,token,30L,TimeUnit.DAYS);
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
        return new UserVO(user.getId(), user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getImg(), token);
    }
}
