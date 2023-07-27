package com.auth.service;

import cn.hutool.core.bean.BeanUtil;
import com.auth.dao.UserDetailRepository;
import com.auth.dao.UserRepository;
import com.auth.pojo.base.User;
import com.auth.pojo.base.UserDetail;
import com.auth.pojo.vo.DetailVO;
import com.common.core.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService {
    @Autowired
    private UserDetailRepository detailRepository;

    public void saveDetail(UserDetail detail,Long id){
       Optional<UserDetail> d = detailRepository.findUserDetailByUserId(id);
       if (d.isEmpty())
           Asserts.fail("用户未找到");
       User user = new User();
       user.setId(id);
       detail.setUser(user);
       detailRepository.save(detail);
    }

    public DetailVO findDetailByUserId(Long id){
        Optional<UserDetail> d = detailRepository.findUserDetailByUserId(id);
        if (d.isEmpty())
            Asserts.fail("用户未找到");
        DetailVO vo = new DetailVO();
        BeanUtil.copyProperties(d.get(),vo);
        vo.setUserId(id);
        return vo;
    }
}
