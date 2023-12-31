package com.auth.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
     * @param name 部门名称
     * @param  principalId 负责人id
     * @return 存储的部门信息
     */
    public DeptVO saveDept(String name,Long principalId){
        Department de = new Department(name,principalId);
        de.setDeleated(0);
        Department department = departmentRepository.save(de);
        return createVO(department);
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
     * 根据传入的状态修改对应的部门
     * @param id 部门id
     * @param del 需要被修改的状态
     */
    public synchronized void changeDeptStatus(Long id,Integer del){
        Department department = departmentRepository.findDepartmentById(id);
        if (ObjectUtil.isEmpty(department)||del<0||del>10)
            Asserts.fail("未找到指定部门信息");
        if (Objects.equals(del, department.getDeleated()))return;
        department.setDeleated(del);
        departmentRepository.save(department);
    }

    /**
     * 修改部门信息
     * @param id 部门id
     * @param name 部门名称
     * @param principalId 部门负责人id
     * @return 修改后的部门信息
     */
    public DeptVO updateDept(Long id,String name,Long principalId){
        Department department = departmentRepository.findDepartmentById(id);
        if (ObjectUtil.isEmpty(department))
            Asserts.fail("未找到指定部门信息");
        department.setName(name);
        department.setPrincipalId(principalId);
        departmentRepository.save(department);
        return createVO(department);
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
        List<Department> d = departmentRepository.findAll();
        return createVO(d);
    }

    /**
     * 根据用户id获取对应部门
     * @param id 用户id
     * @return 部门列表
     */
    public List<DeptVO> getDeptsByUserId(Long id){
        List<UserToDept> utds = userToDeptRepository.findUserToDeptById_UserId(id);
        List<DeptVO> vos = new ArrayList<>();
        for (UserToDept utd:utds) {
            DeptVO vo = new DeptVO();
            BeanUtil.copyProperties(utd.getDepartment(),vo);
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 更新用户和部门之间的关系
     * @param userId 用户id
     * @param deptIds 修改后的部门id
     * @return 修改后的部门列表
     */
    @Transactional
    public List<DeptVO> changeUserToDept(Long userId,Long[] deptIds){
        Integer integer = userToDeptRepository.deleteByUserId(userId);
        if (integer == 0)
            Asserts.fail("没有删除数据");
        if (deptIds.length == 0)return new ArrayList<>();
        List<Department> departments = departmentRepository.findDepartmentByIdIn(deptIds);
        if (departments.isEmpty())
            Asserts.fail("未找到对应部门信息");
        User user = userRepository.findUserById(userId);
        List<UserToDept> utds = new ArrayList<>();
        for (Department d:departments) {
            UserDeptId udi = new UserDeptId(userId,d.getId());
            UserToDept utd = new UserToDept(udi,user,d);
            utds.add(utd);
        }
        userToDeptRepository.saveAll(utds);
        return createVO(departments);
    }


    public List<DeptVO> createVO(List<Department> list){
        List<DeptVO> vos = new ArrayList<>();
        for (Department d:list) {
            vos.add(new DeptVO(d.getId(),d.getName(),d.getPrincipalId(),d.getDeleated()));
        }
        return vos;
    }

    public DeptVO createVO(Department d){
        return new DeptVO(d.getId(),d.getName(),d.getPrincipalId(),d.getDeleated());
    }


}
