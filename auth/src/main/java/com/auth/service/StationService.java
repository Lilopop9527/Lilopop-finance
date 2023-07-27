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

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserToStationRepository userToStationRepository;
    public void saveStation(Station station){
        stationRepository.save(station);
    }

    public List<Station> listStation(Integer del){
        return stationRepository.findStationsByDeleatedOrderByCreateTime(del);
    }

    public void setUsers(List<Long> usersId,Long stationId){
        userToStationRepository.saveAll(getUserToStation(usersId, stationId));
    }

    public void delUsers(List<Long> usersId,Long stationId){
        userToStationRepository.deleteAll(getUserToStation(usersId, stationId));
    }

    public Page<UserToStation> findUsersByStationId(Long stationId, Integer pageNum, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        return userToStationRepository.findUserToStationsById_StationId(stationId, pageable);
    }

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
