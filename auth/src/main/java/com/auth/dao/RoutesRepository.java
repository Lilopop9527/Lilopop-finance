package com.auth.dao;

import com.auth.pojo.base.Routes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoutesRepository extends JpaRepository<Routes,Long> {

    int deleteRoutesByIdIn(List<Long> ids);
    List<Routes> findRoutesByDeleatedAndIdIn(Integer deleated,List<Long> id);
    @Query("select routes.id from role_to_routes where role.id = :id")
    List<Long> findRoutesIdByRoleId(@Param("id") Long id);
    List<Routes> findRoutesByIdIn(Long[] id);
}
