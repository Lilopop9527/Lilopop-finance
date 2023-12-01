package com.auth;

import cn.hutool.core.util.ObjectUtil;
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
import org.springframework.data.domain.Pageable;

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
    UserRepository userRepository;
    @Autowired
    UserToDeptRepository userToDeptRepository;
    @Autowired
    UserToRoleRepository userToRoleRepository;
    @Autowired
    MinioUtil minioUtils;
    @Test
    void Roletest(){
        User u = userRepository.findById(1L).get();
        Role r = roleRepository.findRoleById(3L);
        UserRoleId uri = new UserRoleId(u.getId(),r.getId());
        UserToRole utr = new UserToRole(uri,u,r);
        userToRoleRepository.save(utr);
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
//        Routes routes = new Routes("角色设置","/authConfig/roleConfig",6L);
//        routes.setDeleated(0);
//        routesRepository.save(routes);
        Routes routes = routesRepository.getReferenceById(7L);
        List<Role> all = roleRepository.findAll();
        List<RoleToRoutes> rtrs = new ArrayList<>();
        for(Role r:all){
            RoleRoutesId rri = new RoleRoutesId(r.getId(),routes.getId());
            RoleToRoutes rtr = new RoleToRoutes(rri,r,routes);
            rtrs.add(rtr);
        }
        roleToRoutesRepository.saveAll(rtrs);
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
        //departmentRepository.setData();
    }
    @Test
    void roleInitData(){
        long count = userRepository.count();
        Role role = roleRepository.findRoleById(3L);
        for (int i = 0; i < count/500+1; i++) {
            Pageable pageable = PageRequest.of(i,500);
            Page<User> page = userRepository.findAll(pageable);
            List<User> list = page.getContent();
            List<UserToRole> userToRoles = new ArrayList<>();
            for (User u:list) {
                List<UserToRole> roles = userToRoleRepository.findUserToRolesById_UserId(u.getId());
                if (ObjectUtil.isEmpty(roles)){
                    UserRoleId userRoleId = new UserRoleId(u.getId(),3L);
                    UserToRole userToRole = new UserToRole(userRoleId,u,role);
                    userToRoles.add(userToRole);
                }
            }
            userToRoleRepository.saveAll(userToRoles);
        }
    }

    @Test
    void RoleTest(){
        Routes routes = new Routes();
        routes.setId(120L);
        Role role = new Role();
        role.setId(1L);
        RoleRoutesId rri = new RoleRoutesId(1L,120L);
        RoleToRoutes rtr = new RoleToRoutes(rri,role,routes);
        roleToRoutesRepository.save(rtr);
    }
    @Test
    void routeTest(){
        Routes routes = new Routes();
        routes.setId(121L);
        Role role = new Role();
        role.setId(1L);
        RoleRoutesId rri = new RoleRoutesId(1L,121L);
        RoleToRoutes rtr = new RoleToRoutes(rri,role,routes);
        roleToRoutesRepository.save(rtr);
    }
}
