package com.auth.controller;

import com.auth.pojo.vo.DeptVO;
import com.auth.pojo.vo.StationVO;
import com.auth.service.StationService;
import com.common.core.pojo.CommonData;
import com.common.core.pojo.enums.Weight;
import com.common.interceptor.annotation.CheckRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Lilopop
 * @description:
 */
@RestController
@RequestMapping("/sta")
public class StationController {
    @Autowired
    private StationService stationService;


    @GetMapping("/l")
    @CheckRole
    public CommonData<List<StationVO>> getAllStations(){
        return new CommonData<>(200,"success",stationService.getAllStationsVO());
    }

    @GetMapping("/la")
    @CheckRole(5000000)
    public CommonData<List<StationVO>> getAllStationsByAll(){
        List<StationVO> vos = stationService.getAllStation();
        return new CommonData<>(200,"success",vos);
    }

    @GetMapping("/get")
    @CheckRole
    public CommonData<List<StationVO>> getStationByUserId(Long id){
        List<StationVO> vos = stationService.getStationByUserId(id);
        return new CommonData<>(200,"success",vos);
    }

    @PutMapping("/uts")
    @CheckRole(5000000)
    public CommonData<List<StationVO>> userToSta(Long userId, Long[] staIds){
        List<StationVO> vos = stationService.changeUserToSta(userId, staIds);
        return new CommonData<>(200,"success",vos);
    }

    @PutMapping("/del")
    @CheckRole(5000000)
    public CommonData<StationVO> changeStaStatus(Long id,Integer del){
        StationVO vo = stationService.changeStationStatus(id, del);
        return new CommonData<>(200,"success",vo);
    }

    @PostMapping("/add")
    @CheckRole(5000000)
    public CommonData<StationVO> addStation(String name){
        StationVO vo = stationService.saveStation(name);
        return new CommonData<>(200,"success",vo);
    }

    @PutMapping("/update")
    @CheckRole(5000000)
    public CommonData<StationVO> updateStation(String name,Long id){
        StationVO vo = stationService.changeStation(id, name);
        return new CommonData<>(200,"success",vo);
    }
}
