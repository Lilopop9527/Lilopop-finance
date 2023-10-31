package com.auth.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.auth.dao.*;
import com.auth.pojo.base.*;
import com.auth.pojo.vo.*;
import com.common.core.exception.Asserts;
import com.common.core.pojo.PageBody;
import com.common.core.utils.JWTUtil;
import com.common.minio.utils.MinioUtil;
import io.minio.Result;
import io.minio.messages.DeleteError;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
/**
 *@author: Lilopop
 *@description:用户基本信息
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserToRoleRepository userToRoleRepository;
    @Autowired
    private UserToDeptRepository userToDeptRepository;
    @Autowired
    private UserToStationRepository userToStationRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MinioUtil minioUtils;

    private final String BUCKETNAME = "userimg";
    /**
     * 保存用户信息
     * @param user 用户对象
     * @return 存储结果
     */
    public boolean saveUser(User user){
        String username = user.getUsername();
        String email = user.getEmail();
        User u = null;
        if (ObjectUtil.isNotEmpty(username)){
            u = userRepository.findUserByUsername(username);
        }
        if (ObjectUtil.isEmpty(u)&&ObjectUtil.isNotEmpty(email)){
            u = userRepository.findUserByEmail(email);
        }
        if(ObjectUtil.isNotEmpty(u)){
            return false;
            //Asserts.fail(200,"用户已存在");
        }
        user.setDeleated(0);
        UserDetail detail = new UserDetail();
        detail.setDeleated(0);
        user.setUserDetail(detail);
        detail.setUser(user);
        u = userRepository.save(user);
        Role role = roleRepository.findRoleById(3L);
        UserToRole userToRole = new UserToRole(new UserRoleId(u.getId(),role.getId()),u,role);
        userToRoleRepository.save(userToRole);
        return true;
    }
    /**
     * 更新用户信息（待处理）
     * @param u 用户对象
     */
    public void updateUser(User u){
        Optional<User> o = userRepository.findById(u.getId());
        if(o.isEmpty()){
            Asserts.fail("用户基础信息不存在");
        }
        User user = o.get();
//        if(ObjectUtil.isNotEmpty(u.getUsername())){
//            user.setUsername(u.getUsername());
//        }
        if(ObjectUtil.isNotEmpty(u.getPassword())){
            user.setPassword(u.getPassword());
        }
        if(ObjectUtil.isNotEmpty(u.getPhone())){
            user.setPhone(u.getPhone());
        }
        if(ObjectUtil.isNotEmpty(u.getEmail())){
            user.setEmail(u.getEmail());
        }
        if(ObjectUtil.isNotEmpty(u.getImg())){
            user.setImg(u.getImg());
        }
        userRepository.save(user);
    }

    /**
     * 无条件搜索用户信息
     * @param pageNum 需要查询的页数
     * @param pageSize 每页数据量
     * @return 分页对象
     */
    public PageBody<UserVO> listUser(Integer pageNum, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<User> page = userRepository.findAll(pageable);
        List<UserVO> vos = new ArrayList<>();
        for (User u:page.getContent()) {
            vos.add(createUser(u));
        }
        PageBody<UserVO> pb = new PageBody<>(page.getTotalElements(),vos,pageNum+1,pageSize);
        return pb;
    }

    /**
     * 根据用户名查找用户数据
     * @param pageNum 需要查询的页数
     * @param pageSize
     * @param username 用户名关键字
     * @return 分页对象
     */
    public PageBody<UserVO> listUsernameByPage(Integer pageNum, Integer pageSize, String username){
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        StringBuilder bu = new StringBuilder("%");
        bu.append(username).append("%");
        Page<User> page = userRepository.findByUsernameLikeAndUsernameIsNotNullOrderById(bu.toString(),pageable);
        List<UserVO> vos = new ArrayList<>();
        for (User u:page.getContent()) {
            vos.add(createUser(u));
        }
        PageBody<UserVO> pb = new PageBody<>(page.getTotalElements(),vos,pageNum+1,pageSize);
        return pb;
    }
    /**
     * 根据邮箱查找用户数据
     * @param pageNum 需要查询的页数
     * @param pageSize 每页数据量
     * @param email 邮箱关键字
     * @return 分页对象
     */
    public PageBody<UserVO> listEmailByPage(Integer pageNum, Integer pageSize, String email){
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        StringBuilder bu = new StringBuilder("%");
        bu.append(email).append("%");
        Page<User> page = userRepository.findByEmailLikeAndEmailIsNotNullOrderById(bu.toString(),pageable);
        List<UserVO> vos = new ArrayList<>();
        for (User u:page.getContent()) {
            vos.add(createUser(u));
        }
        PageBody<UserVO> pb = new PageBody<>(page.getTotalElements(),vos,pageNum+1,pageSize);
        return pb;
    }
    /**
     * 根据手机号查找用户数据
     * @param phone 手机号
     * @return user对象
     */
    public PageBody<UserVO> getByPhone(String phone){
        Optional<User> o = userRepository.findUserByPhoneAndPhoneIsNotNull(phone);
        if (o.isEmpty()){
            return new PageBody<>();
        }
        List<UserVO> vos = new ArrayList<>();
        vos.add(createUser(o.get()));
        return new PageBody<>(1L,vos,1,1);
    }

    /**
     * 根据部门查询用户
     * @param pageNum 页码
     * @param pageSize 每页容量
     * @param deptId 部门id
     * @return 分页对象
     */
    public PageBody<UserVO> getByDept(Integer pageNum, Integer pageSize, Long deptId){
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<UserToDept> page = userToDeptRepository.findUserToDeptByDepartmentId(deptId, pageable);
        List<UserVO> users = new ArrayList<>();
        for (UserToDept utd:page.getContent()) {
            users.add(createUser(utd.getUser()));
        }
        return new PageBody<>(page.getTotalElements(),users,pageNum+1,pageSize);
    }

    /**
     * 根据岗位信息筛选用户
     * @param pageNum 页码
     * @param pageSize 容量
     * @param staId 岗位id
     * @return userVO集合
     */
    public PageBody<UserVO> getBySta(Integer pageNum, Integer pageSize, Long staId){
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<UserToStation> page = userToStationRepository.findUserToStationsByStationId(staId,pageable);
        List<UserVO> users = new ArrayList<>();
        for (UserToStation uts:page.getContent()) {
            users.add(createUser(uts.getUser()));
        }
        return new PageBody<>(page.getTotalElements(),users,pageNum+1,pageSize);
    }

    /**
     * 修改逻辑删除状态
     * @param id 用户id
     * @param deleted 删除状态
     * @return 修改后的用户
     */
    public synchronized UserVO changeUserDeleted(Long id,Integer deleted){
        Optional<User> o = userRepository.findById(id);
        if (o.isEmpty()){
            Asserts.fail("用户不存在");
        }
        User user = o.get();
        if (Objects.equals(user.getDeleated(), deleted)||deleted<0||deleted>1||user.getId()== 1L)
            return createUser(user);
        user.setDeleated(deleted);
        User u = userRepository.save(user);
        return createUser(u);
    }

    /**
     * 批量修改用户状态
     * @param ids 用户id
     * @param status 需要被修改成的状态
     * @return 修改后的用户
     */
    public List<UserVO> changeAllStatus(Long[] ids,Integer[] status){
        List<UserVO> list = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            list.add(changeUserDeleted(ids[i],status[i]));
        }
        return list;
    }

    /**
     * 根据角色查询对应用户
     * @param roleIds 角色id列表
     * @return userVO列表
     */
    public List<UserVO> getUsersByRole(List<Long> roleIds){
        List<UserVO> vos = new ArrayList<>();
        List<UserToRole> roles = userToRoleRepository.findUserToRolesById_RoleIdIn(roleIds);
        for (UserToRole ur:roles) {
            User u = ur.getUser();
            UserVO vo = new UserVO();
            BeanUtil.copyProperties(u,vo);
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 只更新username字段
     * @param username 新username
     * @param id 用户id
     * @return 更新结果
     */
    public boolean updateUsername(String username,Long id){
        int res = userRepository.updateUsername(username, id);
        return res == 1;
    }

    /**
     * 只更新email字段
     * @param email 新email
     * @param id 用户id
     * @return 更新结果
     */
    public boolean updateEmail(String email,Long id){
        int res = userRepository.updateEmail(email, id);
        return res == 1;
    }

    /**
     * 只更新phone字段
     * @param phone 新email
     * @param id 用户id
     * @return 更新结果
     */
    public boolean updatePhone(String phone,Long id){
        int res = userRepository.updatePhone(phone, id);
        return res == 1;
    }

    /**
     * 修改密码
     * @param password1 输入密码
     * @param password2 修改后的密码
     * @param id 用户id
     * @return 更新结果
     */
    public boolean updatePassword(String password1,String password2,Long id){
        User user = userRepository.findUserById(id);
        if (!ObjectUtil.equals(user.getPassword(),password1)){
            Asserts.fail("输入密码与原始密码不一致");
        }
        if (ObjectUtil.equals(user.getPassword(),password2)){
            Asserts.fail("修改后的密码与原始密码一致");
        }
        int res = userRepository.updatePassword(password2,id);
        return res == 1;
    }

    /**
     * 修改用户头像
     * @param file 用户头像图片
     * @param id 用户id
     */
    public String updateImg(MultipartFile file,Long id){
        String url = "";
        try{
            minioUtils.existBucket(BUCKETNAME);
            url = minioUtils.getThumb(file);
        }catch (Exception e){
            e.printStackTrace();
            Asserts.fail("minio服务器错误");
        }
        String img = userRepository.getImgUrl(id);
        if (ObjectUtil.equals(url,file.getOriginalFilename())){
            try {
                url = minioUtils.upload(new MultipartFile[]{file}).get(0);
            }catch (Exception e){
                Asserts.fail("新头像上传minio失败");
            }
        }
        Integer i = userRepository.updateImgUrl(id, url);
        if (i!=1){
            List<String> names = new ArrayList<>();
            names.add(url);
            minioUtils.removeObjects(BUCKETNAME,names);
            Asserts.fail("数据库更新失败");
        }
        if (ObjectUtil.isNotEmpty(img)){
            List<String> names = new ArrayList<>();
            names.add(img);
            Iterable<Result<DeleteError>> results = minioUtils.removeObjects(BUCKETNAME, names);
            if (ObjectUtil.isNotEmpty(results)){
                Asserts.fail("删除旧头像失败:"+img);
            }
        }
        return url;
    }

    /**
     * 将user对象转换为userVO对象，并存储token
     * @param user user对象
     * @return userVO对象
     */
    public UserVO createUser(User user){
        List<Role> roles = new ArrayList<>();
        for (UserToRole utr:user.getRoles()) {
            Role r = utr.getRole();
            roles.add(r);
        }
        List<DeptVO> deptVOS = deptToVO(user.getDepts());
        List<StationVO> stationVOS = staToVO(user.getStations());
        DetailVO detailVO = detailToVO(user.getUserDetail());
        UserVO vo = new UserVO(user.getId(), user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getImg(), null,roleToVO(roles),detailVO);
        vo.setDepts(deptVOS);
        vo.setStations(stationVOS);
        vo.setDeleated(user.getDeleated());
        return vo;
    }

    /**
     * 将role对象转换为roleVO对象
     * @param roles role对象
     * @return roleVO对象
     */
    public List<RoleVO> roleToVO(List<Role> roles){
        List<RoleVO> vos = new ArrayList<>();
        for (Role r:roles) {
            vos.add(new RoleVO(r.getId(),r.getRoleName()));
        }
        return vos;
    }

    /**
     * 获取部门VO
     * @param list 用户和部门关系
     * @return 部门VO
     */
    public List<DeptVO> deptToVO(List<UserToDept> list){
        List<DeptVO> vos = new ArrayList<>();
        for (UserToDept utd:list) {
            DeptVO deptVO = new DeptVO();
            BeanUtil.copyProperties(utd.getDepartment(),deptVO);
            vos.add(deptVO);
        }
        return vos;
    }
    /**
     * 获取岗位VO
     * @param list 用户和岗位关系
     * @return 岗位VO
     */
    public List<StationVO> staToVO(List<UserToStation> list){
        List<StationVO> vos = new ArrayList<>();
        for (UserToStation uts:list) {
            StationVO staVO = new StationVO();
            BeanUtil.copyProperties(uts.getStation(),staVO);
            vos.add(staVO);
        }
        return vos;
    }

    /**
     * 将用户详细信息转换成VO
     * @param detail 用户详细信息
     * @return vo
     */
    public DetailVO detailToVO(UserDetail detail){
        DetailVO vo = new DetailVO();
        BeanUtil.copyProperties(detail,vo);
        vo.setUserId(detail.getUser().getId());
        return vo;
    }
}
