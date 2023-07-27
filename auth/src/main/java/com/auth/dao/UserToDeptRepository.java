package com.auth.dao;

import com.auth.pojo.base.UserDeptId;
import com.auth.pojo.base.UserToDept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserToDeptRepository extends JpaRepository<UserToDept, UserDeptId> {
}
