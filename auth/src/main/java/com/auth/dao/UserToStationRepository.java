package com.auth.dao;

import com.auth.pojo.base.UserStationId;
import com.auth.pojo.base.UserToStation;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserToStationRepository extends JpaRepository<UserToStation, UserStationId> {

    Page<UserToStation> findUserToStationsById_StationId(Long id, Pageable pageable);
    Page<UserToStation> findUserToStationsByStationId(Long id,Pageable pageable);
    List<UserToStation> findUserToStationsById_UserId(Long id);
    @Transactional
    @Modifying
    @Query("delete from UserToStation u where u.user.id = :id")
    Integer deleteByUserId(@Param("id") Long id);
}
