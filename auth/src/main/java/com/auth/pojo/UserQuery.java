package com.auth.pojo;

import cn.hutool.core.util.ObjectUtil;
import com.common.core.pojo.Query;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserQuery implements Query {
    private String username;
    private String email;
    private String phone;
    private Date start;
    private Date end;
    private Integer deleated;

    public UserQuery() {
    }

    public UserQuery( String username, String email, String phone, Date start, Date end, Integer deleated) {
        this.username = "%"+username+"%";
        this.email = "%"+email+"%";
        this.phone = "%"+phone+"%";
        this.start = start;
        this.end = end;
        this.deleated = deleated;
    }

    @Override
    public Map<String, Object> getQueryBody() {
        Map<String,Object> map = new HashMap<>();
        if(ObjectUtil.isNotEmpty(username))
            map.put("username",username);
        if(ObjectUtil.isNotEmpty(phone))
            map.put("phone",phone);
        if(ObjectUtil.isNotEmpty(email))
            map.put("email",email);
        if(ObjectUtil.isNotEmpty(start))
            map.put("start",start);
        if(ObjectUtil.isNotEmpty(end))
            map.put("end",end);
        if(ObjectUtil.isNotEmpty(deleated))
            map.put("deleated",deleated);
        return map;
    }
}
