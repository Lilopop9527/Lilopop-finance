package com.auth.dao;

import com.auth.pojo.base.User;
import com.auth.pojo.base.UserDeptId;
import com.auth.pojo.base.UserToDept;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserToDeptRepository extends JpaRepository<UserToDept, UserDeptId> {
    Page<UserToDept> findUserToDeptByDepartmentId(Long id, Pageable pageable);
    List<UserToDept> findUserToDeptById_UserId(Long id);
    @Transactional
    @Modifying
    @Query("delete from UserToDepartment u where u.user.id = :id")
    Integer deleteByUserId(@Param("id") Long id);
}
