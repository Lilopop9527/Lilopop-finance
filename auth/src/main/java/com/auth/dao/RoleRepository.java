package com.auth.dao;

import com.auth.pojo.base.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {
    @Query(value = "select * from ROLE where id in (:ids) and deleated = :del",nativeQuery = true)
    List<Role> getRoles(@Param("ids") List<Long> roles,@Param("del") Integer del);

    Role findRoleById(Long id);
}
