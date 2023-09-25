package com.auth;

import com.auth.dao.RoleRepository;
import com.auth.dao.RoleToRoutesRepository;
import com.auth.pojo.base.Role;
import com.auth.pojo.base.RoleRoutesId;
import com.auth.pojo.base.RoleToRoutes;
import com.auth.service.RoleService;
import com.auth.service.RoutesService;
import com.common.minio.utils.MinioUtil;
import io.minio.errors.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class AnotherTest {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    RoleToRoutesRepository roleToRoutesRepository;
    @Autowired
    RoutesService routesService;
    @Autowired
    MinioUtil minioUtils;
    @Test
    void Roletest(){
        List<Long> id = new ArrayList<>();
        id.add(1L);
        id.add(2L);
        List<RoleToRoutes> rtrs = roleToRoutesRepository.findRoleToRoutesById_RoleIdIn(id);
        System.out.println(rtrs);
    }

    @Test
    void saveRole(){
        Role r1 = new Role("管理员");
        Role r2 = new Role("工程师");
        r1.setDeleated(0);
        r2.setDeleated(0);
        List<Role> list = new ArrayList<>();
        list.add(r1);
        list.add(r2);
        roleRepository.saveAll(list);
    }

    @Test
    void saveRoleToRoute(){
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(3L);
        routesService.saveRoleToRoutes(list,2L);
    }

    @Test
    void minioTest() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioUtils.makeBucket("userimg");
    }
}
