package com.auth.controller;

import com.auth.pojo.vo.StationVO;
import com.auth.service.StationService;
import com.common.core.pojo.CommonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    public CommonData<List<StationVO>> getAllStations(){
        return new CommonData<>(200,"success",stationService.getAllStationsVO());
    }
}
