package com.auth.service;

import com.auth.dao.DepartmentRepository;
import com.auth.dao.UserRepository;
import com.auth.dao.UserToDeptRepository;
import com.auth.pojo.base.Department;
import com.auth.pojo.base.User;
import com.auth.pojo.base.UserDeptId;
import com.auth.pojo.base.UserToDept;
import com.common.core.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeptService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserToDeptRepository userToDeptRepository;

    public void saveDept(Department department){
        departmentRepository.save(department);
    }

    public void saveDeptPrincipal(Long userId,Long deptId){
        Optional<Department> o1 = departmentRepository.findById(deptId);
        Optional<User> o2 = userRepository.findById(userId);
        if (o1.isEmpty()||o2.isEmpty())
            Asserts.fail("未找到指定数据");
        Department department = o1.get();
        department.setPrincipalId(userId);
        departmentRepository.save(department);
    }

    public void saveUserToDept(List<Long> userIds, Long deptId){
        List<UserToDept> ups = userToDept(userIds, deptId);
        userToDeptRepository.saveAll(ups);
    }

    public void delUserToDept(List<Long> userIds, Long deptId){
        List<UserToDept> ups = userToDept(userIds, deptId);
        userToDeptRepository.deleteAll(ups);
    }

    public List<UserToDept> userToDept(List<Long> userIds,Long deptId){
        List<UserToDept> list = new ArrayList<>();
        List<User> o1 = userRepository.findAllById(userIds);
        Optional<Department> o2 = departmentRepository.findById(deptId);
        if (o1.isEmpty()||o2.isEmpty())
            Asserts.fail("数据错误");
        Department department = o2.get();
        for (User u:o1) {
            list.add(new UserToDept(new UserDeptId(u.getId(),department.getId()),u,department));
        }
        return list;
    }

    public List<Department> listPage(Integer pageNum,Integer pageSize){
        //TODO 分页对象应该封装一下
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Department> page = departmentRepository.findDepartmentByDeleated(0, pageable);
        return page.toList();
    }
}
