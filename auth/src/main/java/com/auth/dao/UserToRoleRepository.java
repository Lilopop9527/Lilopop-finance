package com.auth.dao;

import com.auth.pojo.base.UserRoleId;
import com.auth.pojo.base.UserToRole;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserToRoleRepository extends JpaRepository<UserToRole, UserRoleId> {

    List<UserToRole> findUserToRolesById_RoleIdIn(List<Long> roleIds);

    Page<UserToRole> findUserToRolesById_RoleId(Long id, Pageable pageable);

    List<UserToRole> findUserToRolesById_UserId(Long id);

    @Transactional
    @Modifying
    @Query("delete from UserToRole u where u.user.id = :id")
    Integer deleteByUserId(@Param("id") Long id);
}
