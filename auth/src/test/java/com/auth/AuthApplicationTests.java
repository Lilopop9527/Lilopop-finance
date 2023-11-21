package com.auth;

import cn.hutool.core.util.ObjectUtil;
import com.auth.dao.*;
import com.auth.pojo.base.*;
import com.auth.pojo.vo.DetailVO;
import com.auth.pojo.vo.UserVO;
import com.auth.service.*;
import com.common.core.pojo.PageBody;
import com.github.javafaker.Faker;
import jakarta.annotation.Resource;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class AuthApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    StringRedisTemplate template ;
    @Autowired
    LoginService loginService;
    @Autowired
    UserDetailService detailService;
    @Autowired
    UserDetailRepository detailRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserToDeptRepository userToDeptRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    DeptService deptService;
    @Autowired
    StationRepository stationRepository;
    @Autowired
    StationService stationService;
    @Autowired
    UserToStationRepository uts;
    @Autowired
    RoutesRepository routesRepository;

    Faker faker = new Faker();
    @Test
    void contextLoads() {
        Optional<User> u = userRepository.findById(1L);
        u.ifPresentOrElse(System.out::println,null);
    }
    @Test
    void test1(){
        String name = faker.name().firstName(),password = faker.name().nameWithMiddle();
        User user = new User(name,password,
                "189654212241",
                "12345",
                "");
        //user.setId(1L);

        UserDetail detail = new UserDetail();
        detail.setFirstName(faker.name().firstName());
        detail.setLastName(faker.name().lastName());
        //detail.setUser(user1);
        //detailRepository.save(detail);
        //userService.saveUser(user);
        //User user1 = userRepository.findById(1L).get();
        //user1.setUserDetail(detail);
        //userService.saveUser(user1);
        detail.setUser(user);
        detailRepository.save(detail);
        System.out.println(loginService.loginByUsername(name, password));
    }
    @Test
    //@Transactional
    void test2(){
        UserDetail detail = detailRepository.findById(3L).get();
        detail.setHeight(185);
        detailRepository.save(detail);
        //detailRepository.update(184,3L);
    }

    @Test
    void test3(){
        List<Routes> list = new ArrayList<>();
        Routes r1 = new Routes("/","/");
        r1.setDeleated(0);
        Routes r2 = new Routes("首页","/home");
        r2.setDeleated(0);
        Routes r3 = new Routes("个人中心","/userCenter");
        r3.setDeleated(0);
        list.add(r1);
        list.add(r2);
        list.add(r3);
        routesRepository.saveAll(list);
    }

    @Test
    void test4(){

    }

    @Test
    void test5(){
        UserVO vo = loginService.loginByUsername("Lilopop_001","e10adc3949ba59abbe56e057f20f883e");
        System.out.println(vo);
    }

    @Test
    void test6(){
        DetailVO vo = detailService.findDetailByUserId(109L);
        System.out.println(vo);
    }

    @Test
    void test7(){
        List<Long> l = new ArrayList<>();
        l.add(1l);
        l.add(2l);
        List<UserVO> usersByRole = userService.getUsersByRole(l);
        System.out.println(usersByRole);
    }
    @Test
    void test8(){
//        List<Department> list = new ArrayList<>();
//        for (int i = 0; i < 9; i++) {
//            Department department = new Department();
//            department.setName("技术部" + i);
//            list.add(department);
//        }
//
//        departmentRepository.saveAll(list);
    //deptService.saveDeptPrincipal(53l,5l);
//    List<Long> list = new ArrayList<>();
//    list.add(53l);
//    list.add(109l);
//    list.add(54l);
//    deptService.delUserToDept(list,5l);
        List<Department> list = deptService.listPage(1, 5);
    }

    @Test
    void test9(){

    }

    @Test
    void test10(){
        //PageBody<UserVO> p = userService.listUsernameByPage(0,20,"Lilopop");
        PageBody<UserVO> p1 = userService.listUser(10,20);
        System.out.println(p1.getData());
    }

    @Test
    void stationTest(){
        List<Long>[] arr = new List[100];
        Arrays.setAll(arr,e->new ArrayList<>());
        for (int i = 3; i <1003 ; i++) {
            arr[i%100].add((long) i);
        }
        for (int i = 1; i <101 ; i++) {
            stationService.setUsers(arr[i-1],i*1L);
        }
    }

    @Test
    void detailTest(){
//        List<User> users = userRepository.findAll();
//        for (User user:users) {
//            Optional<UserDetail> o = detailRepository.findUserDetailByUserId(user.getId());
//            if (o.isEmpty()){
//                UserDetail detail = new UserDetail();
//                UserDetail save = detailRepository.save(detail);
//                save.setUser(user);
//                user.setUserDetail(save);
//                detailRepository.save(save);
//            }
//        }
       // detailRepository.removeUserDetailByDeleatedIsNull();
    }

    @Test
    void deptTest(){
//        User user = userRepository.findUserById(4L);
//        Department department = departmentRepository.findById(5L).get();
//        UserDeptId userDeptId = new UserDeptId(user.getId(),department.getId());
//        UserToDept userToDept = new UserToDept(userDeptId,user,department);
//        userToDeptRepository.save(userToDept);
//        List<UserToDept> u = userToDeptRepository.findUserToDeptById_UserId(4L);
//        u.removeAll(u);
//        userToDeptRepository.save(u);
        //userToDeptRepository.deleteByUserId(4L);
        //userRepository.save(user);
        Long[] ids = new Long[]{1l,2l,3l};
        List<Department> list = departmentRepository.findDepartmentByIdIn(ids);
        System.out.println(list);
    }
}
