package com.auth;

import cn.hutool.core.util.ObjectUtil;
import com.auth.dao.*;
import com.auth.pojo.base.*;
import com.auth.pojo.vo.DetailVO;
import com.auth.pojo.vo.UserVO;
import com.auth.service.*;
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
    DepartmentRepository departmentRepository;
    @Autowired
    DeptService deptService;
    @Autowired
    StationRepository stationRepository;
    @Autowired
    StationService stationService;
    @Autowired
    UserToStationRepository uts;

    Faker faker = new Faker();
    @Test
    void contextLoads() {
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

    }

    @Test
    void test4(){
        Page<User> page = userService.listUsernameByPage(5,10,"Lilopop");
        System.out.println(page);
    }

    @Test
    void test5(){
        UserDetail detail = new UserDetail(faker.name().firstName(),faker.name().lastName(),"1993-03-12",
                "1234567890",184,209,"A","coding",
                "");
        detail.setId(12L);
        User user = new User();
        user.setId(109l);
        detail.setUser(user);
        detailRepository.save(detail);

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
//        Station station = new Station("科长");
//        station.setDeleated(0);
//        Station station1 = new Station("经理");
//        station1.setDeleated(0);
//        Set<Station> set = new HashSet<>();
//        set.add(station);
//        set.add(station1);
//        stationRepository.saveAll(set);
//        List<Station> stations = stationService.listStation(0);
//        stations.forEach(System.out::println);
//        List<Long> list = new ArrayList<>();
//        list.add(31L);
//        list.add(32L);
//        stationService.setUsers(list,1L);
    stationService.findUsersByStationId(1L,1,1).getContent().forEach(System.out::println);
    }

    @Test
    void test10(){

    }
}
