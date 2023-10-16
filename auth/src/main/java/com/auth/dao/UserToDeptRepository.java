package com.auth.dao;

import com.auth.pojo.base.User;
import com.auth.pojo.base.UserDeptId;
import com.auth.pojo.base.UserToDept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserToDeptRepository extends JpaRepository<UserToDept, UserDeptId> {
    Page<UserToDept> findUserToDeptByDepartmentId(Long id, Pageable pageable);
}
