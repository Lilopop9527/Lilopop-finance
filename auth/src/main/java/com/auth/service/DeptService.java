package com.auth.service;

import com.auth.dao.DepartmentRepository;
import com.auth.dao.UserRepository;
import com.auth.dao.UserToDeptRepository;
import com.auth.pojo.base.Department;
import com.auth.pojo.base.User;
import com.auth.pojo.base.UserDeptId;
import com.auth.pojo.base.UserToDept;
import com.auth.pojo.vo.DeptVO;
import com.auth.pojo.vo.UserVO;
import com.common.core.exception.Asserts;
import com.common.core.pojo.PageBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 *@author: Lilopop
 *@description:部门信息处理业务
 */
@Service
public class DeptService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserToDeptRepository userToDeptRepository;

    /**
     * 存储部门信息
     * @param department 部门信息
     */
    public void saveDept(Department department){
        departmentRepository.save(department);
    }

    /**
     * 设置部门负责人
     * @param userId 负责人id
     * @param deptId 部门id
     */
    public void saveDeptPrincipal(Long userId,Long deptId){
        Optional<Department> o1 = departmentRepository.findById(deptId);
        Optional<User> o2 = userRepository.findById(userId);
        if (o1.isEmpty()||o2.isEmpty())
            Asserts.fail("未找到指定数据");
        Department department = o1.get();
        department.setPrincipalId(userId);
        departmentRepository.save(department);
    }

    /**
     * 存储用户和部门关系
     * @param userIds 用户id列表
     * @param deptId 部门id
     */
    public void saveUserToDept(List<Long> userIds, Long deptId){
        List<UserToDept> ups = userToDept(userIds, deptId);
        userToDeptRepository.saveAll(ups);
    }
    /**
     * 删除用户和部门关系
     * @param userIds 用户id列表
     * @param deptId 部门id
     */
    public void delUserToDept(List<Long> userIds, Long deptId){
        List<UserToDept> ups = userToDept(userIds, deptId);
        userToDeptRepository.deleteAll(ups);
    }

    /**
     * 获取用户和部门关联
     * @param userIds 用户id列表
     * @param deptId 部门id
     * @return 用户和部门关联对象
     */
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

    /**
     * 获取所有部门信息
     * @param pageNum 当前查询页
     * @param pageSize 每页数据量
     * @return 部门信息列表
     */
    public List<Department> listPage(Integer pageNum,Integer pageSize){
        //TODO 分页对象应该封装一下
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Department> page = departmentRepository.findDepartmentByDeleated(0, pageable);
        return page.toList();
    }

    /**
     * 获取所有未禁用的部门
     * @return
     */
    public List<DeptVO> getAllDepts(){
        List<Department> d = departmentRepository.findAllByDeleated(0);
        return createVO(d);
    }
    public PageBody<UserVO> listUserByDept(Integer pageNum,Integer pageSize,Long deptId){
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        return null;
    }

    public List<DeptVO> createVO(List<Department> list){
        List<DeptVO> vos = new ArrayList<>();
        for (Department d:list) {
            vos.add(new DeptVO(d.getId(),d.getName(),d.getPrincipalId(),d.getDeleated()));
        }
        return vos;
    }
}
