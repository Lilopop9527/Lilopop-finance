package com.auth.dao;

import com.auth.pojo.base.RoleRoutesId;
import com.auth.pojo.base.RoleToRoutes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleToRoutesRepository extends JpaRepository<RoleToRoutes, RoleRoutesId> {

    List<RoleToRoutes> findRoleToRoutesById_RoleId(Long id);
    List<RoleToRoutes> findRoleToRoutesById_RoleIdIn(List<Long> id);
}
