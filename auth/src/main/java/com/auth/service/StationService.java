package com.auth.service;

import com.auth.dao.StationRepository;
import com.auth.dao.UserRepository;
import com.auth.dao.UserToStationRepository;
import com.auth.pojo.base.*;
import com.auth.pojo.vo.DeptVO;
import com.auth.pojo.vo.StationVO;
import com.common.core.exception.Asserts;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 *@author: Lilopop
 *@description:岗位信息
 */
@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserToStationRepository userToStationRepository;

    /**
     * 存储岗位信息
     * @param station station对象
     */
    public void saveStation(Station station){
        stationRepository.save(station);
    }

    /**
     * 根据删除状态查询岗位信息
     * @param del 删除状态
     * @return station列表
     */
    public List<Station> listStation(Integer del){
        return stationRepository.findStationsByDeleatedOrderByCreateTime(del);
    }

    /**
     * 设置用户岗位
     * @param usersId 用户id列表
     * @param stationId 岗位id
     */
    public void setUsers(List<Long> usersId,Long stationId){
        userToStationRepository.saveAll(getUserToStation(usersId, stationId));
    }
    /**
     * 删除用户岗位
     * @param usersId 用户id列表
     * @param stationId 岗位id
     */
    public void delUsers(List<Long> usersId,Long stationId){
        userToStationRepository.deleteAll(getUserToStation(usersId, stationId));
    }

    /**
     * 根据岗位搜索对应用户
     * @param stationId 岗位id
     * @param pageNum 需要查询的页数
     * @param pageSize 每页数据量
     * @return 分页对象
     */
    public Page<UserToStation> findUsersByStationId(Long stationId, Integer pageNum, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        return userToStationRepository.findUserToStationsById_StationId(stationId, pageable);
    }

    /**
     * 创建岗位和用户中间关系
     * @param usersId 用户id列表
     * @param stationId 岗位id
     * @return 岗位和用户关系列表
     */
    public Set<UserToStation> getUserToStation(List<Long> usersId,Long stationId){
        Set<UserToStation> set = new HashSet<>();
        Station station = stationRepository.findStationById(stationId);
        for (Long u:usersId) {
            User user = userRepository.findUserById(u);
            UserToStation us = new UserToStation(new UserStationId(u,stationId),user,station);
            set.add(us);
        }
        return set;
    }

    /**
     * 获取所有岗位信息
     * @return 岗位列表
     */
    public List<StationVO> getAllStationsVO(){
        List<Station> list = stationRepository.findStationsByDeleatedOrderByCreateTime(0);
        List<StationVO> vos = new ArrayList<>();
        for (Station s:list) {
            vos.add(createVO(s));
        }
        return vos;
    }

    public StationVO createVO(Station station){
        return new StationVO(station.getId(),station.getName());
    }

    /**
     * 根据用户id查找岗位
     * @param id 用户id
     * @return 岗位列表
     */
    public List<StationVO> getStationByUserId(Long id){
        List<UserToStation> utss = userToStationRepository.findUserToStationsById_UserId(id);
        List<StationVO> vos = new ArrayList<>();
        for (UserToStation uts:utss) {
            vos.add(createVO(uts.getStation()));
        }
        return vos;
    }

    /**
     * 更新用户和岗位之间的关系
     * @param userId 用户id
     * @param staIds 修改后的部门id
     * @return 修改后的岗位列表
     */
    @Transactional
    public List<StationVO> changeUserToSta(Long userId, Long[] staIds){
        Integer integer = userToStationRepository.deleteByUserId(userId);
        if (integer == 0)
            Asserts.fail("没有删除数据");
        if (staIds.length == 0)return new ArrayList<>();
        List<Station> stations = stationRepository.findStationByIdIn(staIds);
        if (stations.isEmpty())
            Asserts.fail("未找到对应部门信息");
        User user = userRepository.findUserById(userId);
        List<UserToStation> utds = new ArrayList<>();
        for (Station d:stations) {
            UserStationId udi = new UserStationId(userId,d.getId());
            UserToStation utd = new UserToStation(udi,user,d);
            utds.add(utd);
        }
        userToStationRepository.saveAll(utds);
        return createVOs(stations);
    }

    public List<StationVO> createVOs(List<Station> stations){
        List<StationVO> vos = new ArrayList<>();
        for (Station s:stations) {
            vos.add(createVO(s));
        }
        return vos;
    }
}
