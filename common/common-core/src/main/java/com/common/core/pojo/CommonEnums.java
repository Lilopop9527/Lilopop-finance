package com.common.core.pojo;

public enum CommonEnums {
    OK(200,"ok"),ERROR(500,"error"),UNFOUND(404,"未找到指定路径"),
    UNAUTHORIZED(401,"未登录"),FORBIDDEN(403,"权限不足");
    private Integer key;
    private String value;

    CommonEnums(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
