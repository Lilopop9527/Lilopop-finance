package com.auth;

import com.auth.dao.RoleRepository;
import com.auth.pojo.base.Role;
import com.auth.service.RoleService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class AnotherTest {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RoleService roleService;
    @Test
    void Roletest(){
        //Role role = roleRepository.findById(1L).get();
        List<Long> list = new ArrayList<>();
        list.add(1l);
        list.add(2l);
        roleService.saveUserToRole(53L,list);
    }

    @Test
    void test1(){
        List<Long> l = new ArrayList<>();
        l.add(1l);
        l.add(2l);
        List<Role> roles = roleRepository.getRoles(l,0);
        for (Role r:roles) {
            System.out.println(r);
        }
    }
}
