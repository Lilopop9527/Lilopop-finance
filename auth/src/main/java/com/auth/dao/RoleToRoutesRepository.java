package com.auth.dao;

import com.auth.pojo.base.RoleRoutesId;
import com.auth.pojo.base.RoleToRoutes;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleToRoutesRepository extends JpaRepository<RoleToRoutes, RoleRoutesId> {

    List<RoleToRoutes> findRoleToRoutesById_RoleId(Long id);
    List<RoleToRoutes> findRoleToRoutesById_RoleIdIn(List<Long> id);
    @Modifying
    @Transactional
    @Query("delete from role_to_routes rtr where rtr.role.id = :id")
    Integer delRoleToRoute(@Param("id") Long id);
}
