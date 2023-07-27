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
    @Query("select u.id,u.username,u.password,u.phone,u.email,u.cteateTime,u.deleated from User u where u.username = :username and u.username is not null or u.email = :email and u.email is not null or u.phone = :phone and u.phone is not null")
    Object findOneUser(@Param("username") String username, @Param("email") String email,@Param("phone") String phone);
    Page<User> findByUsernameLikeAndUsernameIsNotNullOrderById(String username, Pageable pageable);
    Page<User> findByEmailLikeAndEmailIsNotNull(String email, Pageable pageable);
    User findUserById(Long id);
}
