package com.auth;

import com.auth.dao.*;
import com.auth.pojo.base.*;
import com.auth.service.DeptService;
import com.auth.service.RoleService;
import com.auth.service.RoutesService;
import com.common.minio.utils.MinioUtil;
import io.minio.errors.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
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
    RoutesRepository routesRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    DeptService deptService;
    @Autowired
    UserToDeptRepository userToDeptRepository;
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

    @Test
    void initData1(){
//        Routes routes = new Routes("用户搜素","/userList",0L);
//        List<Routes> l = new ArrayList<>();
//        l.add(routes);
//        routesService.saveRoutes(l);
        RoleRoutesId rri = new RoleRoutesId(1L,4L);
        RoleToRoutes rtr = new RoleToRoutes(rri,roleRepository.findRoleById(1L),
                routesRepository.getReferenceById(4L));
        roleToRoutesRepository.save(rtr);
    }

    @Test
    void initData2(){
//        for (int i = 20; i <40 ; i++) {
//            Department d = new Department("测试部"+i,null);
//            d.setDeleated(0);
//            departmentRepository.save(d);
//        }
        List<Long>[] arr = new List[20];
        Arrays.setAll(arr,e->new ArrayList<>());
        for (int i = 4; i < 1001; i++) {
            arr[i%20].add((long) i);
        }
        for (int i = 0; i < 20; i++) {
            deptService.saveUserToDept(arr[i],i* 1L+1);
        }
    }

    @Test
    void deptTest(){
        Page<UserToDept> userToDeptByDepartmentId = userToDeptRepository.findUserToDeptByDepartmentId(1L, PageRequest.of(0, 20));
        System.out.println(userToDeptByDepartmentId);
    }
}
