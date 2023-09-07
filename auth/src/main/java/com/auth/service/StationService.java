package com.auth.service;

import com.auth.dao.StationRepository;
import com.auth.dao.UserRepository;
import com.auth.dao.UserToStationRepository;
import com.auth.pojo.base.Station;
import com.auth.pojo.base.User;
import com.auth.pojo.base.UserStationId;
import com.auth.pojo.base.UserToStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
