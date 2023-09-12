package com.auth.pojo.vo;

import java.util.List;

public class UserVO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String img;
    private String token;
    private DetailVO detail;
    private List<RoleVO> roles;
    private List<RouteVO> routes;
    public UserVO() {
    }

    public UserVO(Long id, String username, String email, String phone, String img, String token,List<RoleVO> vos,DetailVO detail) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.img = img;
        this.token = token;
        this.roles = vos;
        this.detail = detail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<RoleVO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleVO> roles) {
        this.roles = roles;
    }

    public List<RouteVO> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteVO> routes) {
        this.routes = routes;
    }

    public DetailVO getDetail() {
        return detail;
    }

    public void setDetail(DetailVO detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", img='" + img + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
