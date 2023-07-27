package com.auth.dao;

import com.auth.pojo.base.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationRepository extends JpaRepository<Station,Long> {

    List<Station> findStationsByDeleatedOrderByCreateTime(Integer deleated);
    Station findStationById(Long id);
}
