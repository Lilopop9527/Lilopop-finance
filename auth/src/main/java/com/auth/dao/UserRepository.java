package com.auth.dao;

import com.auth.pojo.base.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByUsernameAndPassword(String username,String password);
    Optional<User> findUserByPhoneAndPhoneIsNotNull(String phone);
    Optional<User> findUserByEmailAndPassword(String email,String password);
    @NotNull
    Optional<User> findById(@NotNull Long id);
    Page<User> findByUsernameLikeAndUsernameIsNotNullOrderById(String username, Pageable pageable);
    Page<User> findByEmailLikeAndEmailIsNotNullOrderById(String email, Pageable pageable);
    //Page<User> findUsersOrderById(Pageable pageable);
    User findUserById(Long id);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    @Modifying
    @Query("update User set username = :username where id = :id and deleated = 0")
    @Transactional
    Integer updateUsername(@Param("username")String username,@Param("id")Long id);
    @Modifying
    @Query("update User set email = :email where id = :id and deleated = 0")
    @Transactional
    Integer updateEmail(@Param("email")String email,@Param("id")Long id);
    @Modifying
    @Query("update User set phone = :phone where id = :id and deleated = 0")
    @Transactional
    Integer updatePhone(@Param("phone")String phone,@Param("id")Long id);
    @Modifying
    @Query("update User set password=:password where id=:id and deleated = 0")
    @Transactional
    Integer updatePassword(@Param("password")String password,@Param("id")Long id);
    @Query("select u.img from User u where u.id = :id and u.deleated = 0")
    String getImgUrl(@Param("id") Long id);
    @Modifying
    @Query("update User set img = :img where id = :id and deleated = 0")
    @Transactional
    Integer updateImgUrl(@Param("id") Long id,@Param("img")String img);
}
