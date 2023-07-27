package com.auth.dao;

import com.auth.pojo.base.UserStationId;
import com.auth.pojo.base.UserToStation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserToStationRepository extends JpaRepository<UserToStation, UserStationId> {

    Page<UserToStation> findUserToStationsById_StationId(Long id, Pageable pageable);
}
