package com.auth.dao;

import com.auth.pojo.base.Routes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutesRepository extends JpaRepository<Routes,Long> {

    int deleteRoutesByIdIn(List<Long> ids);
}
