package com.auth.dao;

import com.auth.pojo.base.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByUsernameAndPassword(String username,String password);
    Optional<User> findUserByPhoneAndPhoneIsNotNull(String phone);
    Optional<User> findUserByUsernameAndUsernameIsNotNull(String username);
    Optional<User> findUserByEmailAndEmailIsNotNull(String email);
    Optional<User> findUserByEmailAndPassword(String email,String password);
    Optional<User> findById(Long id);
    Page<User> findByUsernameLikeAndUsernameIsNotNullOrderById(String username, Pageable pageable);
    Page<User> findByEmailLikeAndEmailIsNotNull(String email, Pageable pageable);
    User findUserById(Long id);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
}
