package com.auth.dao;

import com.auth.pojo.base.UserDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<UserDetail,Long> {
    Optional<UserDetail> findById(Long id);
//    @Query("update UserDetail u set u.height = :height where u.id = :id")
//    @Modifying
//    void update(@Param("height") Integer height, @Param("id") Long id);
    Optional<UserDetail> findUserDetailByUserId(Long userId);
//    @Modifying
//    @Query("update UserDetail u set u.deleated = 0 where u.deleated is null ")
//    @Transactional
//    Integer removeUserDetailByDeleatedIsNull();
}
