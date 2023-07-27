package com.auth.dao;

import com.auth.pojo.base.UserRoleId;
import com.auth.pojo.base.UserToRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserToRoleRepository extends JpaRepository<UserToRole, UserRoleId> {

    List<UserToRole> findUserToRolesById_RoleIdIn(List<Long> roleIds);

    Page<UserToRole> findUserToRolesById_RoleId(Long id, Pageable pageable);
}
