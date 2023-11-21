package com.auth.controller;

import com.auth.pojo.vo.DeptVO;
import com.auth.pojo.vo.StationVO;
import com.auth.service.StationService;
import com.common.core.pojo.CommonData;
import com.common.core.pojo.enums.Weight;
import com.common.interceptor.annotation.CheckRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
